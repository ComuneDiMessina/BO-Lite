package it.almaviva.impleme.bolite.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "CaseFile")

public class CaseFileMetaDataDTO {
	private String caseFileType;
}
