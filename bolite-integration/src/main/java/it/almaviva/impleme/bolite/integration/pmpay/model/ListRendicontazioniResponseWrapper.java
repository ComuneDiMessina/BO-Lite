package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RispostaListRendicontazioni
 */
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
public class ListRendicontazioniResponseWrapper   {
  @JsonProperty("PMPAY_ListRendicontazioni")
  private ListRendicontazioniResponse listRendicontazioniResponse;

  
}

