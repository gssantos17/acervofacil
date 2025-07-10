package br.com.acervofacil.api.dto.mapper;

import br.com.acervofacil.api.dto.request.FuncionarioDTO;
import br.com.acervofacil.api.dto.request.FuncionarioUpdateDTO;
import br.com.acervofacil.api.dto.response.FuncionarioResponseDTO;
import br.com.acervofacil.domain.entity.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {

    Funcionario funcionarioDTOToFuncionario(FuncionarioDTO funcionarioDTO);

    @Mapping(target = "contatoPath", expression =
            "java(funcionario.getContato() != null ? \"contatos/\" + funcionario.getContato().getId() : null)")
    @Mapping(target = "enderecoPath", expression =
            "java(funcionario.getEndereco() != null ? \"enderecos/\" + funcionario.getEndereco().getId() : null)")
    @Mapping(target = "usuarioPath", expression =
            "java(funcionario.getUsuario() != null ? \"usuarios/\" + funcionario.getUsuario().getId() : null)")
    @Mapping(target = "cargo", expression = "java(funcionario.getCargo().name())")
    FuncionarioResponseDTO funcionarioToFuncionarioResponseDTO(Funcionario funcionario);

    @Mapping(target = "id", ignore = true)
    Funcionario atualizarFuncionarioAPartirDTO(FuncionarioUpdateDTO dto);
}
