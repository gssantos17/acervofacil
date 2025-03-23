package br.com.acervofacil.configuration.mapper;

import br.com.acervofacil.api.dto.request.EnderecoDTO;
import br.com.acervofacil.api.dto.response.EnderecoResponseDTO;
import br.com.acervofacil.domain.entity.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco enderecoDTOToEndereco(EnderecoDTO enderecoDTO);

    EnderecoResponseDTO enderecoToEnderecoDTO(Endereco endereco);
}
