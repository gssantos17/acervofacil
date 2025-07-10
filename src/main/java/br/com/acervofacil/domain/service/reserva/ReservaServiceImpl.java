package br.com.acervofacil.domain.service.reserva;

import br.com.acervofacil.api.dto.request.RequisicaoReservaDTO;
import br.com.acervofacil.api.dto.response.ResumoReservaDTO;
import br.com.acervofacil.api.utils.ServiceUtils;
import br.com.acervofacil.configuration.mapper.ReservaMapper;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.domain.entity.Reserva;
import br.com.acervofacil.domain.enums.StatusReserva;
import br.com.acervofacil.domain.repository.LivroRepository;
import br.com.acervofacil.domain.repository.ReservaRepository;
import br.com.acervofacil.domain.repository.cliente.ClienteRepository;
import br.com.acervofacil.domain.repository.funcionario.FuncionarioRepository;
import br.com.acervofacil.domain.service.reserva.helper.ReservaValidacaoHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ClienteRepository clienteRepository;
    private final LivroRepository livroRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    @Transactional
    @Override
    public ResumoReservaDTO criarReserva(RequisicaoReservaDTO req) {
        Cliente cliente = buscarClienteOuLancar(req.clienteId());
        Funcionario funcionario = buscarFuncionarioOuLancar(req.funcionarioId());
        Livro livro = buscarLivroOuLancar(req.livroId());

        ReservaValidacaoHelper.validarDadosReserva(cliente, livro, funcionario);

        var reserva = Reserva.builder()
                .cliente(cliente)
                .funcionario(funcionario)
                .livro(livro)
                .build();

        ReservaValidacaoHelper.calcularDataExpiracaoReserva(reserva, livro);

        var reservaSalva = reservaRepository.save(reserva);

        return reservaMapper.reservaToResumoReservaDTO(reservaSalva);
    }

    @Override
    public ResumoReservaDTO cancelarReserva(UUID reservaId) {
        return null;
    }

    @Override
    public ResumoReservaDTO atualizarStatusReserva(UUID reservaId, StatusReserva status) {
        return null;
    }

    @Override
    public ResumoReservaDTO buscarReservaPorId(UUID reservaId) {
        Reserva reserva = this.buscarReservaOuLancar(reservaId);
        return reservaMapper.reservaToResumoReservaDTO(reserva);
    }

    @Override
    public Page<ResumoReservaDTO> listarReservasPorStatus(StatusReserva status, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ResumoReservaDTO> listarReservasPorCliente(UUID clienteId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ResumoReservaDTO> listarReservasPorFuncionario(UUID funcionarioId, Pageable pageable) {
        return null;
    }

    @Override
    public boolean verificarDisponibilidadeLivro(UUID livroId) {
        return false;
    }

    @Override
    public Livro buscarLivroOuLancar(UUID uuid) {
        return ServiceUtils.obterOuLancar( livroRepository.findById(uuid), "Livro", uuid.toString());
    }

    @Override
    public Funcionario buscarFuncionarioOuLancar(UUID uuid) {
        return ServiceUtils.obterOuLancar( funcionarioRepository.findById(uuid), "Funcionario", uuid.toString());
    }

    @Override
    public Cliente buscarClienteOuLancar(UUID uuid) {
        return ServiceUtils.obterOuLancar( clienteRepository.findById(uuid), "Cliente", uuid.toString());
    }

    public Reserva buscarReservaOuLancar(UUID uuid) {
        return ServiceUtils.obterOuLancar( reservaRepository.findReservaWithLivroClienteFuncionarioById(uuid), "Reserva", uuid.toString() );
    }



}
