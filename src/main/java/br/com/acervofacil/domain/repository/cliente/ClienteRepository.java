package br.com.acervofacil.domain.repository.cliente;

import br.com.acervofacil.api.projections.ClienteComEnderecoContatoProjecao;
import br.com.acervofacil.api.projections.ClienteResumoProjecao;
import br.com.acervofacil.domain.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.acervofacil.domain.repository.cliente.ClienteRepositoryImpl.*;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional< Cliente > findByCpf(String cpf);
    boolean existsByCpf(String cpf);

    @Query(BUSCAR_CLIENTE_CONTATO_ENDERECO)
    Page<ClienteComEnderecoContatoProjecao> findAllBy(Pageable pageable);

    @Query(BUSCAR_CLIENTE_CONTATO_ENDERECO_POR_NOME)
    List<ClienteComEnderecoContatoProjecao> findAllByNome(String nome);

    @Query(BUSCAR_CLIENTE_RESUMO_POR_ID)
    Optional<ClienteResumoProjecao> findResumoById(UUID id);

    @Query(BUSCAR_CLIENTE_RESUMO_POR_CPF)
    Optional<ClienteResumoProjecao> findResumoByCpf(String cpf);
}
