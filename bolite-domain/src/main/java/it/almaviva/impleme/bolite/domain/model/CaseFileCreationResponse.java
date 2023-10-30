package it.almaviva.impleme.bolite.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "CaseFileCreationResponse")

public class CaseFileCreationResponse {

	@ApiModelProperty(required = true)
	String esito;

	@ApiModelProperty(required = true)
	String descrizioneErrore;

	@ApiModelProperty(required = true)
	String caseFileId;

	public CaseFileCreationResponse(String esito, String descrizioneErrore, String caseFileId) {
		super();
		this.esito = esito;
		this.descrizioneErrore = descrizioneErrore;
		this.caseFileId = caseFileId;
	}

}
