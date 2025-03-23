package br.com.acervofacil.domain.repository;

import br.com.acervofacil.domain.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional< Cliente > findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
