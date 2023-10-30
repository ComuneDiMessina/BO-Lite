package it.almaviva.impleme.bolite.domain.dto.booking.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Valid
@ApiModel(value = "Organizzatore")
public class OrganizzatoreDTO {

    @ApiModelProperty(example = "Almaviva SpA")
    private String ragioneSociale;

    @ApiModelProperty(example = "mario")
    private String name;

    @ApiModelProperty( example = "Rossi")
    private String surname;

    @ApiModelProperty(example = "SLVFNC89A25H642C")
    private String codiFisc;

    @ApiModelProperty(example = "IT123456789")
    private String piva;

    @ApiModelProperty(example = "m.rossi@gmail.com")
    private String email;

    @ApiModelProperty(example = "(+39)3450000000")
    private String telephoneNumber;

    @JsonProperty(required = true)
    @NotNull(message = "Valorizzare il campo indirizzo")
    @ApiModelProperty(required = true)
    private IndirizzoDTO indirizzo;

    @ApiModelProperty(example = "1930-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate dataNascita;

    @ApiModelProperty(example = "Roma")
    private String luogoNascita;

    @JsonProperty(required = true)
    @NotNull(message = "Valorizzare il campo flagEnte")
    @ApiModelProperty(required = true, example = "false", allowableValues = "true, false")
    private Boolean flagEnte;

}