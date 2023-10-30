package it.almaviva.impleme.bolite.domain.dto.casefile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.RichiedenteDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Valid
@Data
@ApiModel(value = "Pratica")
public class CaseFileDTO {

	@NotEmpty
	@ApiModelProperty(required = true, value = "Causale del pagamento", example = "servizi cimiteriali")
	private String causale;

	@ApiModelProperty(required = true, value = "Note alla prenotazione", example = "bla bla")
	private String note;

	@NotNull
	@ApiModelProperty(required = true, value = "Importo", example = "100.30")
	private BigDecimal importo;

	@ApiModelProperty(required = true, value = "Codice Pratica", example = "45a8c9f3-0d38-463a-8914-761916c81584")
	private UUID idCaseFile;

	@NotNull
	@JsonRawValue
	@ApiModelProperty(required = true, value = "Dettagli del tributo", notes = "Campi variabili che dipendono dal tipo di tributo", example = "{\"n° ospiti\":5,\"camere\":[1,2]}")
	private JsonNode details;

	@NotNull
	@Valid
	@ApiModelProperty(required = true, value = "Richiedente")
	private RichiedenteDTO richiedente;

	@ApiModelProperty(value = "Allegati")
	private List<AttachmentDTO> attachments;

	@NotNull
	@ApiModelProperty(required = true, value = "Stato della pratica" ,example = "1")
	private Integer state;

	@NotEmpty
	@ApiModelProperty(required = true, value = "Identifica il Comune", notes = "Verrà aperta una posizione debitoria con riferimento all'ente", example = "SIF07")
	private String ente;

	@NotEmpty
	@ApiModelProperty(required = true, value = "Identifica il tributo", notes = "Verrà aperta una posizione debitoria con riferimento al tributo", example = "01")
	private String tributo;

	@NotEmpty
	@JsonProperty(defaultValue = "00")
	@ApiModelProperty(required = true, value = "Identifica il numero della rata", notes = "Se si paga in unica soluzione inserire 00", example = "01")
	private String rata;

	@NotEmpty
	private String tipologia;

	@ApiModelProperty(required = true)
	@JsonIgnore
	private String path;

	@ApiModelProperty(required = true)
	@JsonIgnore
	private String reportFileName;

	@ApiModelProperty(required = true)
	@JsonIgnore
	private String base64Content;

	@ApiModelProperty(required = true)
	@JsonIgnore
	private String message;

	@ApiModelProperty(required = true)
	@JsonIgnore
	private String messageType;

	@ApiModelProperty(required = true)
	@JsonIgnore
	private UUID bookingId; // serve per fornire l'id della prenotazione al flusso zeebe

	@ApiModelProperty(required = true)
	@JsonIgnore
	private String pdf_template;

	@ApiModelProperty(required = true)
	@JsonIgnore
	private String amountString;


}