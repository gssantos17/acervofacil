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

import java.util.List;
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
        if (StatusEmprestimo.FINALIZADO.equals(emprestimo.getStatus())) {
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

        if (StatusEmprestimo.FINALIZADO.equals(emprestimo.getStatus()) || StatusEmprestimo.CANCELADO.equals(emprestimo.getStatus())) {
            throw new ServiceException("Não é possível atualizar o status. Este empréstimo já está finalizado ou cancelado.");
        }

        if (!StatusEmprestimo.FINALIZADO.equals(novoStatus) && !StatusEmprestimo.CANCELADO.equals(novoStatus)) {
            throw new ServiceException("Apenas os status FINALIZADO ou CANCELADO podem ser atribuídos por um supervisor.");
        }
    }

}
