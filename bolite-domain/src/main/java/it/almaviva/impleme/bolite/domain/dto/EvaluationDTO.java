package it.almaviva.impleme.bolite.domain.dto;

import io.swagger.annotations.ApiModel;
import it.almaviva.impleme.bolite.domain.enums.EEvaluationChoice;
import lombok.Data;

@Data
@ApiModel(value = "Evaluation")

public class EvaluationDTO {
    
    private EEvaluationChoice choice;
    private String message;
}