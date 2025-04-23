package br.com.acervofacil.domain.service.emprestimo.helper;

import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Emprestimo;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.api.dto.request.RequisicaoEmprestimoDTO;
import br.com.acervofacil.domain.enums.Role;
import br.com.acervofacil.domain.enums.StatusEmprestimo;
import br.com.acervofacil.domain.enums.StatusLivro;
import br.com.acervofacil.domain.exception.ServiceException;
import br.com.acervofacil.domain.entity.Multa;
import br.com.acervofacil.domain.enums.StatusMulta;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmprestimoValidacaoHelper {

    private EmprestimoValidacaoHelper() {
    }

    public static void validarDadosEmprestimo(Cliente cliente, Livro livro, RequisicaoEmprestimoDTO dto) {

        if (!StatusLivro.DISPONIVEL.equals(livro.getStatus()) || livro.getQuantidadeDisponivel() <= 0) {
            throw new ServiceException("O empréstimo não pode ser concluído. O livro não está disponível.");
        }

        if (dto.quantidadeEmprestada() >= livro.getQuantidadeDisponivel()) {
            throw new ServiceException("A quantidade disponível é de " + livro.getQuantidadeDisponivel()
                    + ". O cliente não pode obter todos os exemplares.");
        }

        if (cliente.getQuantidadeEmprestimos() > 2) {
            throw new ServiceException("O cliente já possui dois empréstimos em aberto. "
                    + "É necessário devolver pelo menos um para prosseguir.");
        }

        List<Emprestimo> emprestimosAtivos = cliente.getEmprestimos().stream()
                .filter(emprestimo -> emprestimo.getStatus() == StatusEmprestimo.ATIVO)
                .collect(Collectors.toList());

        if (emprestimosAtivos.size() >= 3) {
            throw new ServiceException("O cliente já possui o número máximo de empréstimos ativos permitidos (3). Devolva um exemplar para realizar um novo empréstimo.");
        }

        boolean jaPossuiEsteLivroEmAberto = emprestimosAtivos.stream()
                .anyMatch(emprestimo -> emprestimo.getLivro().getId().equals(livro.getId()));

        if (jaPossuiEsteLivroEmAberto) {
            throw new ServiceException("O cliente já possui um empréstimo ativo deste mesmo livro.");
        }

    }

    public static void validarDevolucaoEmprestimo(Emprestimo emprestimo) {
        if (StatusEmprestimo.CONCLUIDO.equals(emprestimo.getStatus())) {
            throw new ServiceException("Este empréstimo já foi finalizado.");
        }

        if (emprestimo.getStatus() != StatusEmprestimo.ATIVO) {
            throw new ServiceException("Este empréstimo não pode ser devolvido, pois não está ativo.");
        }
    }

    public static void validarAtualizacaoPorSupervisor(Emprestimo emprestimo, StatusEmprestimo novoStatus, Funcionario funcionario) {
        Role role = funcionario.getUsuario().getRole();
        if( !Role.ADMINISTRADOR.equals(role))
            throw new ServiceException("Apenas administradores podem executar esta ação.");

        if (StatusEmprestimo.CONCLUIDO.equals(emprestimo.getStatus()) || StatusEmprestimo.CANCELADO.equals(emprestimo.getStatus())) {
            throw new ServiceException("Não é possível atualizar o status. Este empréstimo já está finalizado ou cancelado.");
        }

        if (!StatusEmprestimo.CONCLUIDO.equals(novoStatus) && !StatusEmprestimo.CANCELADO.equals(novoStatus)) {
            throw new ServiceException("Apenas os status FINALIZADO ou CANCELADO podem ser atribuídos por um supervisor.");
        }
    }

    public static void aplicarMultaSeAtrasado(Emprestimo emprestimo) {
        LocalDateTime prevista = emprestimo.getDataDevolucaoPrevista();
        LocalDateTime real     = emprestimo.getDataDevolucaoReal();

        if (prevista == null || real == null || !real.isAfter(prevista)) {
            return; // Sem multa
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

    private static BigDecimal calcularValorMulta(long diasAtraso) {
        Map<Long, BigDecimal> faixaMulta = new LinkedHashMap<>();
        faixaMulta.put(7L, BigDecimal.valueOf(1.00));  // Até 7 dias: R$1.00 por dia
        faixaMulta.put(15L, BigDecimal.valueOf(1.50)); // Até 15 dias: R$1.50 por dia
        faixaMulta.put(30L, BigDecimal.valueOf(2.00)); // Até 30 dias: R$2.00 por dia
        faixaMulta.put(Long.MAX_VALUE, BigDecimal.valueOf(3.00)); // Acima de 30 dias: R$3.00 por dia

        for (Map.Entry<Long, BigDecimal> entry : faixaMulta.entrySet()) {
            if (diasAtraso <= entry.getKey()) {
                return BigDecimal.valueOf(diasAtraso).multiply(entry.getValue());
            }
        }

        return BigDecimal.ZERO;
    }

}
