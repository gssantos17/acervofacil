package br.com.acervofacil.api.dto.response;

import br.com.acervofacil.domain.enums.StatusMulta;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResumoMultaDTO(
        UUID id,
        BigDecimal valor,
        StatusMulta status,
        LocalDateTime dataMulta
) {}