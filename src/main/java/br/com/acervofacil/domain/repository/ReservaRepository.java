package br.com.acervofacil.domain.repository;

import br.com.acervofacil.domain.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {
}
