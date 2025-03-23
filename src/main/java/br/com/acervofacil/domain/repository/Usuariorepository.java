package br.com.acervofacil.domain.repository;

import br.com.acervofacil.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Usuariorepository extends JpaRepository<Usuario, UUID> {
}
