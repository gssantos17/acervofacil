package br.com.acervofacil.domain.repository.funcionario;

import br.com.acervofacil.api.projections.FuncionarioComPathsProjecao;
import br.com.acervofacil.domain.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

import static br.com.acervofacil.domain.repository.funcionario.FuncionarioRepositoryImpl.BUSCAR_FUNCIONARIO_CONTATO_ENDERECO_USUARIO;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {

    Optional<Funcionario> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    @Query(BUSCAR_FUNCIONARIO_CONTATO_ENDERECO_USUARIO)
    Page<FuncionarioComPathsProjecao> findAllComEnderecoContatoUsuario(Pageable pageable);

}
