package br.com.acervofacil.domain.repository;

import br.com.acervofacil.domain.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
    Optional<Livro> findByIsbn(String isbn);
}
