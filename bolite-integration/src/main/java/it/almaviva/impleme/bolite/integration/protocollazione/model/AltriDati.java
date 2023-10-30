package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class AltriDati {
	@JsonProperty("tipoDocumento")
	private Formato tipoDocumento;
	@JsonProperty("tramite")
	private Formato tramite;
	@JsonProperty("note")
	private String note;
	@JsonProperty("attributo1")
	private String attributo1;
	@JsonProperty("attributo2")
	private String attributo2;
	@JsonProperty("affare")
	private Formato affare;
	@JsonProperty("visibilita")
	private String visibilita;

}
