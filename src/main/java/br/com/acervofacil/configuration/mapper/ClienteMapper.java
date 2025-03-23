package br.com.acervofacil.configuration.mapper;

import br.com.acervofacil.api.dto.request.ClienteDTO;
import br.com.acervofacil.api.dto.response.ClienteResponseDTO;
import br.com.acervofacil.domain.entity.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ContatoMapper.class, EnderecoMapper.class, UsuarioMapper.class})
public interface ClienteMapper {

    Cliente clienteDTOToCliente(ClienteDTO clienteDTO);

    ClienteResponseDTO clienteToClienteResponseDTO(Cliente cliente);
}
