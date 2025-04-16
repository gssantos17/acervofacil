package br.com.acervofacil.domain.repository;

import br.com.acervofacil.domain.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {
    Optional<Autor> findByNome(String nomeAutor);
}
