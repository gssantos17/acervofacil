package br.com.acervofacil.domain.service.emprestimo;

import br.com.acervofacil.api.dto.request.RequisicaoEmprestimoDTO;
import br.com.acervofacil.api.dto.response.ResumoEmprestimoDTO;
import br.com.acervofacil.api.utils.ServiceUtils;
import br.com.acervofacil.configuration.mapper.EmprestimoMapper;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Emprestimo;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.domain.enums.StatusEmprestimo;
import br.com.acervofacil.domain.repository.LivroRepository;
import br.com.acervofacil.domain.repository.cliente.ClienteRepository;
import br.com.acervofacil.domain.repository.emprestimo.EmprestimoRepository;
import br.com.acervofacil.domain.repository.funcionario.FuncionarioRepository;
import br.com.acervofacil.domain.service.emprestimo.helper.EmprestimoValidacaoHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        emprestimo.setStatus( StatusEmprestimo.FINALIZADO );
        Livro livro = emprestimo.getLivro();
        livro.aumentarQuantidadeDisponivel( emprestimo.getQuantidadeEmprestada() );

        Emprestimo emprestimoAtualizado = emprestimoRepository.save( emprestimo );

        return emprestimoMapper.toDto( emprestimoAtualizado );
    }

    @Override
    public ResumoEmprestimoDTO atualizarStatusPorSupervisor(UUID id, StatusEmprestimo novoStatus, UUID idFuncionarioResponsavel) {
        Emprestimo emprestimo = buscarEmprestimoOuLancar(id);
        Funcionario funcionario = buscarFuncionarioOuLancar(idFuncionarioResponsavel);

        EmprestimoValidacaoHelper.validarAtualizacaoPorSupervisor(emprestimo, novoStatus);

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
        // TODO: implementar lógica de busca por ID
        return null;
    }

    @Override
    public List<ResumoEmprestimoDTO> listarTodos() {
        // TODO: implementar lógica de listagem
        return null;
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

    public Emprestimo buscarEmprestimoOuLancar(UUID uuid) {
        return ServiceUtils.obterOuLancar( emprestimoRepository.findById(uuid), "Emprestimo", uuid.toString());
    }
}
