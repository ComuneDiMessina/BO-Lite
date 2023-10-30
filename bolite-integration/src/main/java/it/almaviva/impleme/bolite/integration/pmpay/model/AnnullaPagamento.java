package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * AnnullaPagamento
 */
@Component
@Setter
@Getter
@ToString

public class AnnullaPagamento {
	@JsonProperty("ID_DEBITO")
	private String ID_DEBITO;

	@JsonProperty("IUV")
	private String IUV;

}
