package br.com.acervofacil.domain.repository;

import br.com.acervofacil.domain.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContatoRepository extends JpaRepository< Contato, UUID> {
}
