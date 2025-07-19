package br.com.acervofacil.domain.service.emprestimo;

import br.com.acervofacil.api.dto.request.RequisicaoEmprestimoDTO;
import br.com.acervofacil.api.dto.response.ResumoEmprestimoDTO;
import br.com.acervofacil.api.projections.ResumoEmprestimoProjecao;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.domain.enums.StatusEmprestimo;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface EmprestimoService {

    ResumoEmprestimoDTO cadastrar(RequisicaoEmprestimoDTO dto);

    ResumoEmprestimoDTO finalizarEmprestimo(UUID uuid);

    ResumoEmprestimoDTO atualizarStatusPorSupervisor(UUID id, StatusEmprestimo status, UUID idFuncionarioResponsavel);

    ResumoEmprestimoDTO buscarPorId(UUID id);

    Page<ResumoEmprestimoProjecao> listarTodos(int pagina, int tamanho, String campoOrdenacao, String direcao);

    Cliente buscarClienteOuLancar(UUID uuid);

    Funcionario buscarFuncionarioOuLancar(UUID uuid);

    Livro buscarLivroOuLancar(UUID uuid);

    void pagarEmprestimo(UUID uuid, UUID idFuncionarioResponsavel );
}
