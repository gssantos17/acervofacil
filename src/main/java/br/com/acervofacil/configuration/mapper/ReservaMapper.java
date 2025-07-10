package br.com.acervofacil.configuration.mapper;

import br.com.acervofacil.api.dto.response.ResumoReservaDTO;
import br.com.acervofacil.domain.entity.Reserva;
import br.com.acervofacil.domain.enums.StatusReserva;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    @Mapping(target = "livroId", source = "livro.id")
    @Mapping(target = "tituloLivro", expression = "java(reserva.getLivro() != null ? reserva.getLivro().getTitulo() : null)")
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "nomeCliente", expression = "java(reserva.getCliente() != null ? reserva.getCliente().getNome() : null)")
    @Mapping(target = "funcionarioId", expression = "java(reserva.getFuncionario() != null ? reserva.getFuncionario().getId() : null)")
    @Mapping(target = "nomeFuncionario", expression = "java(reserva.getFuncionario() != null ? reserva.getFuncionario().getNome() : null)")
    @Mapping(target = "dataExpiracao", source = "dataExpiracaoReserva")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    ResumoReservaDTO reservaToResumoReservaDTO(Reserva reserva);


    @Mapping(target = "livro.id", source = "livroId")
    @Mapping(target = "cliente.id", source = "clienteId")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "dataReserva", source = "dataReserva")
    Reserva resumoReservaDTOToReserva(ResumoReservaDTO resumoReservaDTO);

    @Mapping(target = "id", ignore = true)
    void atualizarReservaAPartirDTO(ResumoReservaDTO resumoReservaDTO, @MappingTarget Reserva reserva);

    @Named("stringToStatus")
    static StatusReserva stringToStatus(String status) {
        return status != null ? StatusReserva.valueOf(status) : null;
    }

    @Named("statusToString")
    static String statusToString(StatusReserva status) {
        return status != null ? status.name() : null;
    }
}