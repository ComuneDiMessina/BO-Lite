package it.almaviva.impleme.bolite.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@ApiModel(value = "Tipologia di Pratica")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServizioDTO {


    @NotNull
    @ApiModelProperty(required = true, example = "01", notes = "Identificativo del servizio")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty(required = true, example = "01", notes = "Codice identificativo univoco")
    @NotEmpty(message = "Il campo codice è obbligatorio")
    private String codice;

    @ApiModelProperty(example = "BORw0KGgoAAAANSUhEUgAABXgAAAHxCAYAAADX+9Q0==", notes = "base64 immagine")
    private String img;

    @ApiModelProperty(required = true, example = "Servizi Cimeteriali", notes = "Nome del servizio")
    @NotEmpty
    private String nome;

    @ApiModelProperty(example = "Descrizione", notes = "Descrzione del servizio")
    private String descrizione;

    @ApiModelProperty(example = "Note", notes = "Eventuali note")
    private String note;

    @ApiModelProperty(example = "Testo Libero", notes = "Testo libero")
    private String libero;

    @NotNull(message = "Valorizzare il campo importo")
    @Positive(message = "Inserire un importo maggiore di 0")
    @ApiModelProperty(required = true, value = "Importo dovuto per il pagamento dei diritti di segreteria", notes = "Verrà aperta una posizione debitoria con tale importo. ", example = "100")
    private BigDecimal importo;



}
