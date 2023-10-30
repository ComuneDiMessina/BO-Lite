package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RegistraPagamento
 */
@Component
@Setter
@Getter
@ToString

public class RegistraPagamentoRequest   {
  @JsonProperty("headerRegistraPagamento")
  private HeaderRegistraPagamento headerRegistraPagamento;
  
  @JsonProperty("registraPagamento")
  private RegistraPagamento registraPagamento;

  @JsonProperty("annullaPagamento")
  private AnnullaPagamento annullaPagamento;
  
  @JsonProperty("pagamentoEsterno")
  private PagamentoEsterno pagamentoEsterno;
  
  @JsonProperty("DATI_BOLLO")
  private DatiBollo bollo;
}

