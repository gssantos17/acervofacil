package br.com.acervofacil.domain.service.multa;

import br.com.acervofacil.api.dto.response.ResumoMultaDTO;

import java.util.List;
import java.util.UUID;

public interface MultaService {

    /**
     * Retorna os detalhes de uma multa específica pelo seu ID.
     *
     * @param id UUID da multa.
     * @return DTO com resumo da multa.
     */
    ResumoMultaDTO buscarPorId(UUID id);

    /**
     * Retorna a lista de todas as multas registradas no sistema.
     *
     * @return Lista de DTOs com resumos das multas.
     */
    List<ResumoMultaDTO> listarTodas();

    /**
     * Marca uma multa como paga, registrando a data de pagamento.
     *
     * @param id UUID da multa a ser paga.
     * @return DTO atualizado com o status da multa.
     */
    ResumoMultaDTO pagarMulta(UUID id);

}