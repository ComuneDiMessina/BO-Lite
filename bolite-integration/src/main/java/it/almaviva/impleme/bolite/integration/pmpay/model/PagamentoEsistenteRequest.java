package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * PAGAMENTOESISTENTEREQUEST
 */
@Component
@Setter
@Getter
@ToString

public class PagamentoEsistenteRequest   {
  @JsonProperty("headerPagamento")
  private HeaderPagamentoEsistente headerPagamentoEsistente;

  @JsonProperty("pagamentoEsistente")
  private PagamentoEsistente pagamentoEsistente;

}

