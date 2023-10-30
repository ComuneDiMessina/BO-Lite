package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RispostaRtPdf
 */
@Component
@Setter
@Getter
@ToString

public class RtPdfResponseWrapper   {
  @JsonProperty("GetRTPdfResponse")
  private RtPdfResponse rtPdfResponse;

 
}

