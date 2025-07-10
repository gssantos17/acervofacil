package br.com.acervofacil.api.dto.mapper;

import br.com.acervofacil.api.dto.request.RequisicaoEmprestimoDTO;
import br.com.acervofacil.api.dto.response.ResumoEmprestimoDTO;
import br.com.acervofacil.domain.entity.Cliente;
import br.com.acervofacil.domain.entity.Emprestimo;
import br.com.acervofacil.domain.entity.Funcionario;
import br.com.acervofacil.domain.entity.Livro;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    // Mapeia do DTO de requisição para entidade
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "dataEmprestimo", ignore = true) // gerenciado por @PrePersist
    @Mapping(target = "livro", source = "livroId", qualifiedByName = "mapLivro")
    @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "mapCliente")
    @Mapping(target = "funcionarioResponsavel", source = "funcionarioResponsavelId", qualifiedByName = "mapFuncionario")
    Emprestimo toEntity(RequisicaoEmprestimoDTO dto);

    // Atualiza entidade existente com dados do DTO
    @Mapping(target = "livro", source = "livroId", qualifiedByName = "mapLivro")
    @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "mapCliente")
    @Mapping(target = "funcionarioResponsavel", source = "funcionarioResponsavelId", qualifiedByName = "mapFuncionario")
    void updateFromDto(RequisicaoEmprestimoDTO dto, @MappingTarget Emprestimo emprestimo);

    // Mapeia entidade para DTO de resposta detalhada
    @Mapping(target = "id", expression = "java(java.util.UUID.fromString(String.valueOf(emprestimo.getId())))")
    @Mapping(target = "livroId", expression = "java(java.util.UUID.fromString(String.valueOf(emprestimo.getLivro().getId())))")
    @Mapping(target = "clienteId", expression = "java(java.util.UUID.fromString(String.valueOf(emprestimo.getCliente().getId())))")
    @Mapping(target = "funcionarioResponsavelId", expression = "java(java.util.UUID.fromString(String.valueOf(emprestimo.getFuncionarioResponsavel().getId())))")
    @Mapping(target = "livroTitulo", source = "livro.titulo")
    @Mapping(target = "multa", source = "emprestimo.multa")
    @Mapping(target = "clienteNome", source = "cliente.nome")
    @Mapping(target = "funcionarioResponsavelNome", source = "funcionarioResponsavel.nome")
    ResumoEmprestimoDTO toDto(Emprestimo emprestimo);

    // Métodos auxiliares para construir referências por ID
    @Named("mapLivro")
    default Livro mapLivro(UUID id) {
        Livro livro = new Livro();
        livro.setId(id);
        return livro;
    }

    @Named("mapCliente")
    default Cliente mapCliente(UUID id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }

    @Named("mapFuncionario")
    default Funcionario mapFuncionario(UUID id) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        return funcionario;
    }
}
