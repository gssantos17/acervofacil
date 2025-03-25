package br.com.acervofacil.domain.repository;

import br.com.acervofacil.domain.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
}
