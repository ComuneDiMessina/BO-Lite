package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RispostaRegistraPagamento
 */
@Component
@Setter
@Getter
@ToString

public class RegistraPagamentoResponseWrapper   {
  @JsonProperty("REGISTRAPAGAMENTO_RESPONSE")
  private RegistraPagamentoResponse registraPagamentoResponse;

  
}

