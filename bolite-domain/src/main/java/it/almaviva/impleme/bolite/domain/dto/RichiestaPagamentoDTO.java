package it.almaviva.impleme.bolite.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "RichiestaPagamento")
public class RichiestaPagamentoDTO {
    private Long idPratica;
    private BigDecimal importo;
}