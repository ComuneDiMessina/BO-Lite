package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * PagamentoEsistenteBollettino
 */
@Component
@Setter
@Getter
@ToString

public class PagamentoEsistenteBollettinoRequest   {
  @JsonProperty("PAGAMENTOESISTENTE_REQUEST")
  private PagamentoEsistenteRequest pagamentoEsistenteRequest;  

  @JsonProperty("inline")
  private Boolean inline = null;

  
}

