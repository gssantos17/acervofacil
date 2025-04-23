package br.com.acervofacil.domain.service.emprestimo;

import br.com.acervofacil.api.dto.request.RequisicaoEmprestimoDTO;
import br.com.acervofacil.api.dto.response.ResumoEmprestimoDTO;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.domain.enums.StatusEmprestimo;

import java.util.List;
import java.util.UUID;

public interface EmprestimoService {

    ResumoEmprestimoDTO cadastrar(RequisicaoEmprestimoDTO dto);

    ResumoEmprestimoDTO finalizarEmprestimo(UUID uuid);

    ResumoEmprestimoDTO atualizarStatusPorSupervisor(UUID id, StatusEmprestimo status, UUID idFuncionarioResponsavel);

    void excluir(UUID id);

    ResumoEmprestimoDTO buscarPorId(UUID id);

    List<ResumoEmprestimoDTO> listarTodos();

    Cliente buscarClienteOuLancar(UUID uuid);

    Funcionario buscarFuncionarioOuLancar(UUID uuid);

    Livro buscarLivroOuLancar(UUID uuid);

    void pagarEmprestimo(UUID uuid, UUID idFuncionarioResponsavel );
}
