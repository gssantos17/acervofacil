package br.com.acervofacil.domain.repository;

import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Contato;
import br.com.acervofacil.domain.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContatoRepository extends JpaRepository< Contato, UUID> {
}
