package it.almaviva.impleme.bolite.domain.dto.booking.books;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.NewAttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.LegaleDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.OrganizzatoreDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.PresidenteDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.RichiedenteDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Valid
@ApiModel(value = "RichiestaPrenotazione")
public class NewBookingDTO {

    @NotNull
    @Valid
    @JsonProperty(required = true)
    @ApiModelProperty(position = 1, required = true, notes = "Colui che fa la richiesta sul sistema")
    private RichiedenteDTO richiedente;

    @Valid
    @JsonProperty
    @ApiModelProperty(position = 2, notes = "Può coincidere con il richiedente")
    private OrganizzatoreDTO organizzatore;

    @Valid
    @JsonProperty
    @ApiModelProperty(position = 3, value = "Rappresentante Legale")
    private LegaleDTO legale;

    @Valid
    @JsonProperty
    @ApiModelProperty(position = 4, value = "Presidente")
    private PresidenteDTO presidente;

    @NotNull(message = "Valorizzare il campo roomId")
    @JsonProperty(value = "roomId", required = true)
    @ApiModelProperty(position = 0, required = true, example = "2ca3a7bb-8785-4fba-831d-085ce7f67f4d")
    private UUID roomID;


    @ApiModelProperty( example = "bla bla bla", notes = "Eventuali note")
    @JsonProperty("note")
    private String note;

    @NotNull(message = "Valorizzare il campo  giornoDa")
    @ApiModelProperty(required = true, example = "2020-01-01", notes = "Data inizio prenotazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @JsonProperty(value = "giornoDa", required = true)
    private LocalDate giornoDa;

    @ApiModelProperty(example = "2020-01-05", notes = "Data fine prenotazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @JsonProperty(value = "giornoA")
    private LocalDate giornoA;

    @ApiModelProperty(example = "09:00:00", notes = "Data inizio fascia oraria prenotazione")
    @JsonProperty(value = "oraDa")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime oraDa;

    @ApiModelProperty(example = "10:00:00", notes = "Data fine fascia oraria prenotazione")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty(value = "oraA")
    private LocalTime oraA;

    @ApiModelProperty( required = true, example = "true", notes = "Valorizzato a true se la prenotazione vale per l'intera giornata")
    @JsonProperty(required = true, value = "interaGiornata", defaultValue = "false")
    @NotNull(message = "Valorizzare il campo  flagInteraGiornata")
    private Boolean flagInteraGiornata;

    @ApiModelProperty(required = true, notes = "Servizi accessori prenotati")
    @JsonProperty("serviziPrenotati")
    private List<ServiceDTO> serviziPrenotati;

    @JsonProperty(required = true, value = "tipoEvento" )
    @NotNull(message = "Valorizzare il campo  tipoEvento")
    @ApiModelProperty(example = "1", required = true, notes = "Tipo Evento")
    private Integer tipoEnvento;

    @JsonProperty(required = true)
    @javax.validation.constraints.NotBlank(message = "Valorizzare il campo  titoloEvento")
    @ApiModelProperty( required = true, example = "Matrimonio Mario", notes = "Titolo Evento")
    private String titoloEvento;

    @ApiModelProperty(example = "matrimonio all'aperto", notes = "Descrizione Evento")
    private String descrizioneEvento;

    @Valid
    @ApiModelProperty(notes = "Eventuali allegati")
    private List<NewAttachmentDTO> allegati;

    @ApiModelProperty( required = true, example = "true", notes = "Informativa Privacy")
    @NotNull(message = "Valorizzare il campo  flagPrivacy1")
    private Boolean flagPrivacy1;

    @NotNull(message = "Valorizzare il campo  flagPrivacy2")
    @ApiModelProperty( required = true, example = "true", notes = "Statuto legale")
    private Boolean flagPrivacy2;

    @NotNull(message = "Valorizzare il campo  flagPrivacy3")
    @ApiModelProperty( required = true, example = "true", notes = "Principi costituzione")
    private Boolean flagPrivacy3;

    @NotNull(message = "Valorizzare il campo  flagPrivacy4")
    @ApiModelProperty( required = true, example = "true", notes = "Condizioni Giunta Comunale")
    private Boolean flagPrivacy4;
}
