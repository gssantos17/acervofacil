package br.com.acervofacil.configuration.mapper;

import br.com.acervofacil.api.dto.request.ContatoDTO;
import br.com.acervofacil.api.dto.response.ContatoResponseDTO;
import br.com.acervofacil.domain.entity.Contato;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContatoMapper {

    Contato contatoDTOToContato(ContatoDTO contatoDTO);

    ContatoResponseDTO contatoToContatoDTO(Contato contato);
}
