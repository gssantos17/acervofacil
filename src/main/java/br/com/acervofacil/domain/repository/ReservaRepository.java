package br.com.acervofacil.domain.repository;

import br.com.acervofacil.domain.entity.Reserva;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

    @EntityGraph(attributePaths = {"livro", "cliente", "funcionario"})
    Optional<Reserva> findReservaWithLivroClienteFuncionarioById(UUID id);
}
