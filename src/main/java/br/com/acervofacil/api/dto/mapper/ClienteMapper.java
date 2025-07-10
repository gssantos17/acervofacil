package br.com.acervofacil.api.dto.mapper;

import br.com.acervofacil.api.dto.request.ClienteDTO;
import br.com.acervofacil.api.dto.request.ClienteUpdateDTO;
import br.com.acervofacil.api.dto.response.ClienteResponseDTO;
import br.com.acervofacil.domain.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente clienteDTOToCliente(ClienteDTO clienteDTO);
    Cliente clienteUpdateDTOToCliente(ClienteUpdateDTO clienteUpdateDTO);

    @Mapping(target = "contatoPath", expression =
            "java(cliente.getContato() != null ? \"contatos/\" + cliente.getContato().getId() : null)")
    @Mapping(target = "enderecoPath", expression =
            "java(cliente.getEndereco() != null ? \"enderecos/\" + cliente.getEndereco().getId() : null)")
    @Mapping(target = "usuarioPath", expression =
            "java(cliente.getUsuario() != null ? \"usuarios/\" + cliente.getUsuario().getId() : null)")
    ClienteResponseDTO clienteToClienteResponseDTO(Cliente cliente);
}
