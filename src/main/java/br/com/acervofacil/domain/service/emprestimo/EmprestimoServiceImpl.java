package br.com.acervofacil.domain.service.emprestimo;

import br.com.acervofacil.api.dto.request.RequisicaoEmprestimoDTO;
import br.com.acervofacil.api.dto.response.ResumoEmprestimoDTO;
import br.com.acervofacil.api.dto.response.ResumoMultaDTO;
import br.com.acervofacil.api.projections.ResumoEmprestimoProjection;
import br.com.acervofacil.api.utils.ServiceUtils;
import br.com.acervofacil.configuration.mapper.EmprestimoMapper;
import br.com.acervofacil.domain.entity.*;
import br.com.acervofacil.domain.enums.StatusEmprestimo;
import br.com.acervofacil.domain.enums.StatusMulta;
import br.com.acervofacil.domain.exception.ServiceException;
import br.com.acervofacil.domain.repository.LivroRepository;
import br.com.acervofacil.domain.repository.MultaRepository;
import br.com.acervofacil.domain.repository.cliente.ClienteRepository;
import br.com.acervofacil.domain.repository.emprestimo.EmprestimoRepository;
import br.com.acervofacil.domain.repository.funcionario.FuncionarioRepository;
import br.com.acervofacil.domain.service.emprestimo.helper.EmprestimoValidacaoHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EmprestimoServiceImpl implements EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final EmprestimoMapper emprestimoMapper;

    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final LivroRepository livroRepository;
    private final MultaRepository multaRepository;

    @Transactional
    @Override
    public ResumoEmprestimoDTO cadastrar(RequisicaoEmprestimoDTO dto) {
        Cliente cliente = buscarClienteOuLancar(dto.clienteId());
        Livro livro = buscarLivroOuLancar(dto.livroId());
        livro.diminuirQuantidadeDisponivel(dto.quantidadeEmprestada());
        Funcionario funcionario = buscarFuncionarioOuLancar(dto.funcionarioResponsavelId());

        EmprestimoValidacaoHelper.validarDadosEmprestimo(cliente, livro, dto);

        Emprestimo emprestimo = emprestimoMapper.toEntity(dto);
        emprestimo.setCliente(cliente);
        emprestimo.setFuncionarioResponsavel(funcionario);
        emprestimo.setLivro(livro);
        var emprestimoSalvo = emprestimoRepository.save(emprestimo);

        return emprestimoMapper.toDto(emprestimoSalvo);
    }

    @Override
    public ResumoEmprestimoDTO finalizarEmprestimo(UUID uuid) {
        Emprestimo emprestimo = buscarEmprestimoOuLancar(uuid);
        emprestimo.setDataDevolucaoReal( LocalDateTime.now() );
        EmprestimoValidacaoHelper.validarDevolucaoEmprestimo(emprestimo);
        EmprestimoValidacaoHelper.aplicarMultaSeAtrasado(emprestimo);

        emprestimo.setStatus( StatusEmprestimo.AGUARDANDO_PAGAMENTO );
        Livro livro = emprestimo.getLivro();
        livro.aumentarQuantidadeDisponivel( emprestimo.getQuantidadeEmprestada() );

        Emprestimo emprestimoAtualizado = emprestimoRepository.save( emprestimo );

        return emprestimoMapper.toDto( emprestimoAtualizado );
    }

    @Override
    public ResumoEmprestimoDTO atualizarStatusPorSupervisor(UUID id, StatusEmprestimo novoStatus, UUID idFuncionarioResponsavel) {
        Emprestimo emprestimo = buscarEmprestimoOuLancar(id);
        Funcionario funcionario = buscarFuncionarioOuLancar(idFuncionarioResponsavel);

        EmprestimoValidacaoHelper.validarAtualizacaoPorSupervisor(emprestimo, novoStatus, funcionario);

        emprestimo.setFuncionarioResponsavel(funcionario);
        emprestimo.setStatus(novoStatus);
        emprestimo.setDataDevolucaoReal(LocalDateTime.now());

        // Devolve os livros para o estoque
        Livro livro = emprestimo.getLivro();
        livro.aumentarQuantidadeDisponivel(emprestimo.getQuantidadeEmprestada());

        Emprestimo emprestimoAtualizado = emprestimoRepository.save(emprestimo);

        return emprestimoMapper.toDto(emprestimoAtualizado);
    }

    @Override
    public void excluir(UUID id) {
        // TODO: implementar lógica de exclusão lógica
    }

    @Override
    public ResumoEmprestimoDTO buscarPorId(UUID id) {
        Emprestimo emprestimo = buscarEmprestimoOuLancar(id);
        return emprestimoMapper.toDto(emprestimo);
    }

    @Override
    public Page<ResumoEmprestimoProjection> listarTodos(int pagina, int tamanho, String campoOrdenacao, String direcao) {
        Pageable pageable = ServiceUtils.criarPageable(pagina, tamanho, campoOrdenacao, direcao);
        return emprestimoRepository.listarResumoEmprestimos(pageable);
    }

    public Cliente buscarClienteOuLancar(UUID uuid){
        return ServiceUtils.obterOuLancar( clienteRepository.findById(uuid), "Cliente", uuid.toString());
    }

    public Funcionario buscarFuncionarioOuLancar(UUID uuid) {
        return ServiceUtils.obterOuLancar( funcionarioRepository.findById(uuid), "Funcionario", uuid.toString());
    }

    public Livro buscarLivroOuLancar(UUID uuid) {
        return ServiceUtils.obterOuLancar( livroRepository.findById(uuid), "Livro", uuid.toString());
    }

    @Transactional
    @Override
    public void pagarEmprestimo(UUID uuid, UUID idFuncionarioResponsavel) {
        Emprestimo emprestimo = buscarEmprestimoOuLancar(uuid);

        if (!emprestimo.getStatus().equals(StatusEmprestimo.AGUARDANDO_PAGAMENTO)) {
            throw new ServiceException("Finalize o empréstimo primeiro.");
        }

        Funcionario funcionario = buscarFuncionarioOuLancar(idFuncionarioResponsavel);
        emprestimo.setFuncionarioResponsavel(funcionario);

        Multa multa = emprestimo.getMulta();
        if (multa != null && multa.getStatus() == StatusMulta.PENDENTE) {
            multa.setDataPagamento(LocalDateTime.now());
            multa.setStatus(StatusMulta.PAGA);
        }

        emprestimo.setStatus(StatusEmprestimo.CONCLUIDO);
        emprestimoRepository.save(emprestimo);
    }


    public Emprestimo buscarEmprestimoOuLancar(UUID uuid) {
        return ServiceUtils.obterOuLancar( emprestimoRepository.findById(uuid), "Emprestimo", uuid.toString());
    }

    private void aplicarMultaSeAtrasado(Emprestimo emprestimo) {
        LocalDateTime prevista = emprestimo.getDataDevolucaoPrevista();
        LocalDateTime real     = emprestimo.getDataDevolucaoReal();

        if (prevista == null || real == null || !real.isAfter(prevista)) {
            return;
        }

        long diasAtraso = Duration.between(
                prevista.toLocalDate().atStartOfDay(),
                real.toLocalDate().atStartOfDay()
        ).toDays();

        BigDecimal valor = calcularValorMulta(diasAtraso);

        Multa multa = new Multa();
        multa.setValor(valor);
        multa.setStatus(StatusMulta.PENDENTE);
        multa.setDataMulta(LocalDateTime.now());
        multa.setEmprestimo(emprestimo);
        emprestimo.setMulta(multa);
    }

    private BigDecimal calcularValorMulta(long diasAtraso) {
        if (diasAtraso <= 7) {
            return BigDecimal.valueOf(diasAtraso).multiply(BigDecimal.valueOf(1.00));
        } else if (diasAtraso <= 15) {
            return BigDecimal.valueOf(diasAtraso).multiply(BigDecimal.valueOf(1.50));
        } else if (diasAtraso <= 30) {
            return BigDecimal.valueOf(diasAtraso).multiply(BigDecimal.valueOf(2.00));
        } else {
            return BigDecimal.valueOf(diasAtraso).multiply(BigDecimal.valueOf(3.00));
        }
    }
}
