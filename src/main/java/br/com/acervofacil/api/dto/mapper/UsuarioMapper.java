    package br.com.acervofacil.api.dto.mapper;

    import br.com.acervofacil.api.dto.request.UsuarioDTO;
    import br.com.acervofacil.api.dto.response.UsuarioResponseDTO;
    import br.com.acervofacil.domain.entity.Usuario;
    import org.mapstruct.Mapper;

    @Mapper(componentModel = "spring")
    public interface UsuarioMapper {

        Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO);

        UsuarioResponseDTO usuarioToUsuarioDTO(Usuario usuario);
    }
