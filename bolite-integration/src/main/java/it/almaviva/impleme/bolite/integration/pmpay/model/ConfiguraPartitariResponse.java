package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * PMPAYConfigurazionePartitari
 */
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor

public class ConfiguraPartitariResponse   {
  @JsonProperty("Esito")
  private String esito;

  @JsonProperty("Errore")
  private String errore;

  
}

