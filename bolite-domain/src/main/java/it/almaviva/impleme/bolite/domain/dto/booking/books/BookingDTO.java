package it.almaviva.impleme.bolite.domain.dto.booking.books;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.LegaleDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.OrganizzatoreDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.PresidenteDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.RichiedenteDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@ApiModel(value = "Prenotazione")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    @ApiModelProperty(required = true, example = "1", notes = "Identificativo")
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(required = true, example = "6c54f8a7-f672-4e7c-b197-bf4b6ae249a7", notes = "Identificativo struttura")
    private UUID roomId;

    @ApiModelProperty(required = true, example = "Sala Ovale", value = "Nome della stanza")
    private String nome;

    @ApiModelProperty(required = true, example = "5c660bc3-a494-4098-8d60-aff92cc1ef56", notes = "Numero Pratica")
    @JsonProperty("numeroPratica")
    private UUID numeroPratica;

    @NotNull
    @Valid
    @JsonProperty(required = true)
    @ApiModelProperty(required = true, notes = "Colui che fa la richiesta sul sistema")
    private RichiedenteDTO richiedente;

    @Valid
    @JsonProperty
    @ApiModelProperty(notes = "Evenutualmente coincide con il richiedente")
    private OrganizzatoreDTO organizzatore;


    @ApiModelProperty( required = true, example = "true", notes = "Valorizzato a true se la prenotazione vale per l'intera giornata")
    @JsonProperty(required = true, value = "interaGiornata", defaultValue = "false")
    private Boolean flagInteraGiornata;

    @Valid
    @JsonProperty
    @ApiModelProperty(position = 3, value = "Rappresentante Legale")
    private LegaleDTO legale;

    @NotNull
    @ApiModelProperty(required = true, value = "Identifica il Comune", notes = "Verrà aperta una posizione debitoria con riferimento all'ente", example = "SIF07")
    private String ente;

    @Valid
    @JsonProperty
    @ApiModelProperty(position = 4, value = "Presidente")
    private PresidenteDTO presidente;

    @ApiModelProperty(required = true, example = "1000.30", notes = "Importo totale prenotazione")
    @JsonProperty("importo")
    private BigDecimal importo;

    @ApiModelProperty(required = true, example = "1000.30", notes = "Importo totale prenotazione")
    @JsonProperty("importoServizi")
    private BigDecimal importoServizi;

    @ApiModelProperty( example = "bla bla bla", notes = "Si richiede copia dello statuto")
    @JsonProperty("note")
    private String note;

    @ApiModelProperty( example = "1111111111", notes = "Codice IUV")
    @JsonProperty("iuv")
    private String iuv;

    @ApiModelProperty(required = true, example = "2020-01-01 20:00:00", notes = "Data avvenuta prenotazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "dataPrenotazione")
    private LocalDateTime dataPrenotazione;

    @ApiModelProperty(required = true, example = "1", notes = "Stato Pratica")
    @JsonProperty("stato")
    private Integer stato;

    @ApiModelProperty(example = "d4b039c1-a5b7-4d04-b391-579fe8c2bde7", notes = "Numero di Protocollo")
    @JsonProperty("numeroProtocollo")
    private String protocollo;


    @ApiModelProperty(example = "2020-01-01 20:00:00", notes = "Data avvenuta protocollazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dataProtocollo")
    private LocalDateTime dataProtocollo;

    @NotNull
    @ApiModelProperty(required = true, example = "2020-01-01", notes = "Data inizio prenotazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @JsonProperty(value = "giornoDa", required = true)
    private LocalDate giornoDa;

    @NotNull
    @ApiModelProperty(example = "2020-01-05", notes = "Data fine prenotazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @JsonProperty(value = "giornoA")
    private LocalDate giornoA;

    @NotNull
    @ApiModelProperty(required = true, example = "2020-01-01", notes = "Data inizio fascia oraria prenotazione")
    @JsonProperty(value = "oraDa", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime oraDa;

    @NotNull
    @ApiModelProperty(required = true, example = "2020-01-01", notes = "Data fine fascia oraria prenotazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty(value = "oraA", required = true)
    private LocalTime oraA;

    @ApiModelProperty(required = true, notes = "Servizi accessori utilizzati")
    @JsonProperty("serviziUtilizzati")
    private List<ServiceDTO> serviziUtilizzati;

    @JsonProperty(value = "tipoEvento", required = true)
    @ApiModelProperty(example = "matrimonio", required = true, notes = "Tipo Evento", value = "tipoEvento")
    private String tipoEnvento;

    @ApiModelProperty(example = "matrimonio all'aperto", notes = "Descrizione Evento")
    private String descrizioneEvento;

    @ApiModelProperty(required = true, example = "Matrimonio Mario", notes = "Titolo Evento")
    private String titoloEvento;

    @Valid
    @ApiModelProperty(notes = "Eventuali allegati")
    private List<AttachmentDTO> allegati;
}
