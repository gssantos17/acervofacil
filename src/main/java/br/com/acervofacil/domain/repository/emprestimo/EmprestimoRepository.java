package br.com.acervofacil.domain.repository.emprestimo;

import br.com.acervofacil.api.projections.ResumoEmprestimoProjection;
import br.com.acervofacil.domain.entity.Emprestimo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {

    @Query(value = """
            SELECT
                        e.id AS id,
                        e.livro_id AS livroId,
                        l.titulo AS livroTitulo,
                        e.id_cliente AS clienteId,
                        c.nome AS clienteNome,
                        e.id_funcionario_responsavel AS funcionarioResponsavelId,
                        f.nome AS funcionarioResponsavelNome,
                        e.quantidade_emprestada AS quantidadeEmprestada,
                        e.taxa_emprestimo AS taxaEmprestimo,
                        e.data_emprestimo AS dataEmprestimo,
                        e.data_devolucao_prevista AS dataDevolucaoPrevista,
                        e.data_devolucao_real AS dataDevolucaoReal,
                        e.status AS status
                    FROM tb_emprestimo e
                    JOIN tb_cliente c ON c.id = e.id_cliente
                    JOIN tb_funcionario f ON f.id = e.id_funcionario_responsavel 
                    JOIN tb_livro l ON l.id = e.livro_id     
                    ORDER BY e.data_emprestimo DESC
    """, nativeQuery = true)
    Page<ResumoEmprestimoProjection> listarResumoEmprestimos(Pageable pageable);


}
