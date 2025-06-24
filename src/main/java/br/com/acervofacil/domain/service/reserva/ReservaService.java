package br.com.acervofacil.domain.service.reserva;

import br.com.acervofacil.api.dto.response.ResumoReservaDTO;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.domain.enums.StatusReserva;
import br.com.acervofacil.domain.exception.NaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ReservaService {

    /**
     * Cria uma nova reserva de livro.
     *
     * @param clienteId     ID do cliente que está fazendo a reserva.
     * @param livroId       ID do livro a ser reservado.
     * @param funcionarioId ID do funcionário responsável pela reserva.
     * @return A reserva criada representada por ResumoReservaDTO.
     */
    ResumoReservaDTO criarReserva(UUID clienteId, UUID livroId, UUID funcionarioId);

    /**
     * Cancela uma reserva existente.
     *
     * @param reservaId ID da reserva a ser cancelada.
     * @return A reserva com o status alterado para cancelada, representada por ResumoReservaDTO.
     */
    ResumoReservaDTO cancelarReserva(UUID reservaId);

    /**
     * Atualiza o status de uma reserva (por exemplo, de "ativo" para "concluída").
     *
     * @param reservaId ID da reserva a ser atualizada.
     * @param status    Novo status da reserva.
     * @return A reserva atualizada representada por ResumoReservaDTO.
     */
    ResumoReservaDTO atualizarStatusReserva(UUID reservaId, StatusReserva status);

    /**
     * Recupera uma reserva específica pelo seu ID.
     *
     * @param reservaId ID da reserva.
     * @return Reserva encontrada, representada por ResumoReservaDTO.
     */
    ResumoReservaDTO buscarReservaPorId(UUID reservaId);

    /**
     * Lista todas as reservas ativas ou com base em outros filtros (por exemplo, por cliente, livro ou status).
     *
     * @param status  Status das reservas a serem listadas.
     * @param pageable Parâmetros de paginação (número da página, quantidade de itens por página, ordenação).
     * @return Página de reservas que atendem ao filtro, representadas por ResumoReservaDTO.
     */
    Page<ResumoReservaDTO> listarReservasPorStatus(StatusReserva status, Pageable pageable);

    /**
     * Recupera as reservas de um cliente específico.
     *
     * @param clienteId ID do cliente.
     * @param pageable Parâmetros de paginação.
     * @return Página de reservas do cliente, representadas por ResumoReservaDTO.
     */
    Page<ResumoReservaDTO> listarReservasPorCliente(UUID clienteId, Pageable pageable);

    /**
     * Recupera as reservas feitas por um funcionário específico.
     *
     * @param funcionarioId ID do funcionário.
     * @param pageable Parâmetros de paginação.
     * @return Página de reservas feitas pelo funcionário, representadas por ResumoReservaDTO.
     */
    Page<ResumoReservaDTO> listarReservasPorFuncionario(UUID funcionarioId, Pageable pageable);

    /**
     * Verifica se o livro está disponível para reserva.
     *
     * @param livroId ID do livro.
     * @return True se o livro está disponível para reserva, false caso contrário.
     */
    boolean verificarDisponibilidadeLivro(UUID livroId);

    /**
     * Busca um Livro pelo UUID e lança uma exceção {@link NaoEncontradoException} caso não seja encontrado.
     *
     * @param uuid O UUID do Livro.
     * @return O Livro encontrado.
     * @throws NaoEncontradoException Se o Livro não for encontrado.
     */
    Livro buscarLivroOuLancar(UUID uuid);

    /**
     * Busca um Funcionario pelo UUID e lança uma exceção {@link NaoEncontradoException} caso não seja encontrado.
     *
     * @param uuid O UUID do Funcionario.
     * @return O Funcionario encontrado.
     * @throws NaoEncontradoException Se o Funcionario não for encontrado.
     */
    Funcionario buscarFuncionarioOuLancar(UUID uuid);

    /**
     * Busca um Cliente pelo UUID e lança uma exceção {@link NaoEncontradoException} caso não seja encontrado.
     *
     * @param uuid O UUID do Cliente.
     * @return O Cliente encontrado.
     * @throws NaoEncontradoException Se o Cliente não for encontrado.
     */
    Cliente buscarClienteOuLancar(UUID uuid);
}