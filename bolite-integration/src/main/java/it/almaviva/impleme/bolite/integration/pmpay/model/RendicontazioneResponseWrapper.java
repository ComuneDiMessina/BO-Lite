package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RispostaRendicontazione
 */
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
public class RendicontazioneResponseWrapper   {
  @JsonProperty("PMPAY_Rendicontazione")
  private RendicontazioneResponse rendicontazioneResponse;

 
}

