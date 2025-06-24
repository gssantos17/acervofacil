package br.com.acervofacil.domain.service.reserva.helper;

import br.com.acervofacil.domain.entity.*;
import br.com.acervofacil.domain.enums.StatusEmprestimo;
import br.com.acervofacil.domain.enums.StatusLivro;
import br.com.acervofacil.domain.enums.StatusReserva;
import br.com.acervofacil.domain.enums.StatusUsuario;
import br.com.acervofacil.domain.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaValidacaoHelper {

    private ReservaValidacaoHelper() {
    }

    /**
     * Valida as condições necessárias para realizar uma reserva de livro.
     * Verifica a disponibilidade do livro, o número de reservas do cliente e a quantidade de reservas ativas.
     *
     * @param cliente O cliente que deseja realizar a reserva.
     * @param livro O livro que será reservado.
     * @param funcionario O funcionario que está reservando
     * @throws ServiceException Se alguma das condições de reserva não for atendida.
     */
    public static void validarDadosReserva(Cliente cliente, Livro livro, Funcionario funcionario) {

        if( funcionario.getUsuario().getStatus() != StatusUsuario.ATIVO)
            throw new ServiceException("O funcionário deve está ativo.");

        // Verifica se o livro está disponível para reserva
        if (livro.getQuantidadeDisponivel() <= 0) {
            throw new ServiceException("O livro não está disponível para reserva no momento.");
        }

        // Verifica se o cliente já tem a mesma reserva ativa
        List<Reserva> reservasAtivas = cliente.getReservas().stream()
                .filter(reserva -> reserva.getStatus() == StatusReserva.ATIVO)
                .collect(Collectors.toList());

        if (reservasAtivas.size() >= 3) {
            throw new ServiceException("O cliente já possui o número máximo de reservas ativas permitidas (3).");
        }

        boolean jaPossuiEsteLivroEmReserva = reservasAtivas.stream()
                .anyMatch(reserva -> reserva.getLivro().getId().equals(livro.getId()));

        if (jaPossuiEsteLivroEmReserva) {
            throw new ServiceException("O cliente já possui uma reserva ativa para este livro.");
        }
    }

    /**
     * Calcula a data de expiração de uma reserva, considerando o status do livro (emprestado ou disponível para retirada futura).
     * Se o livro estiver emprestado, a expiração será baseada na data de devolução prevista do empréstimo ativo mais antigo.
     * Se o livro estiver disponível, a expiração será definida com um prazo fixo de 7 dias.
     *
     * @param reserva A reserva que será verificada.
     * @param livro O livro que foi reservado.
     * @throws ServiceException Se o livro não está disponível, se não houver empréstimos ativos ou se não for possível calcular a data de expiração.
     */
    public static void calcularDataExpiracaoReserva(Reserva reserva, Livro livro) {
        if (livro.getStatus() == StatusLivro.DISPONIVEL && livro.getQuantidadeDisponivel() <= 0) {
            Emprestimo emprestimo = livro.getEmprestimos().stream()
                    .filter(e -> e.getStatus() == StatusEmprestimo.ATIVO)
                    .min(Comparator.comparing(Emprestimo::getDataDevolucaoPrevista))
                    .orElseThrow(() -> new ServiceException("Não há empréstimos ativos para o livro."));

            if (emprestimo.getDataDevolucaoPrevista() != null) {
                reserva.setDataExpiracaoReserva(emprestimo.getDataDevolucaoPrevista().plusDays(2));
            } else {
                throw new ServiceException("Não é possível calcular a data de expiração. O livro não tem uma devolução prevista.");
            }
        } else if (livro.getStatus() == StatusLivro.DISPONIVEL) {
            reserva.setDataExpiracaoReserva(LocalDateTime.now().plusDays(7));
        } else {
            throw new ServiceException("O livro não pode ser reservado devido ao seu status atual.");
        }
    }

    /**
     * Valida se a reserva pode ser cancelada, verificando o status da reserva.
     *
     * @param reserva A reserva que será verificada.
     * @throws ServiceException Se a reserva não puder ser cancelada.
     */
    public static void validarCancelamentoReserva(Reserva reserva) {
        if (StatusReserva.CONCLUIDA.equals(reserva.getStatus()) || StatusReserva.CANCELADO.equals(reserva.getStatus())) {
            throw new ServiceException("A reserva não pode ser cancelada, pois já está concluída ou cancelada.");
        }
    }
}