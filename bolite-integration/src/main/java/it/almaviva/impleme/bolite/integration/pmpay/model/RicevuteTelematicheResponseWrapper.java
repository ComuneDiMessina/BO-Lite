package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RispostaRicevuteTelematiche
 */
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor

public class RicevuteTelematicheResponseWrapper   {
  @JsonProperty("PMPAY_RicevuteTelematiche")
  private RicevuteTelematicheResponse ricevuteTelematicheResponse;

 
}

