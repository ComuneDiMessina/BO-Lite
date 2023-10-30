package it.almaviva.impleme.bolite.domain.dto.booking.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Valid
@ApiModel(value = "Debitore")
public class DebitoreDTO {

    @ApiModelProperty(required = true, example = "Mario")
    @NotBlank(message = "Valorizzare il campo name")
    private String name;

    @ApiModelProperty(required = true, example = "Rossi")
    @NotBlank(message = "Valorizzare il campo surname")
    private String surname;

    @ApiModelProperty(required = true,example = "SLVFNC00A00H282X", notes = "Il campo deve essere senza prefisso TINIT-")
    @NotBlank(message = "Valorizzare il campo codiFisc")
    private String codiFisc;

    @ApiModelProperty(required = true, example = "m.rossi@gmail.com")
    @NotBlank(message = "Valorizzare il campo email")
    private String email;

    @ApiModelProperty( example = "(+39)3450000000")
    private String telephoneNumber;

    @JsonProperty(required = true)
    @NotNull(message = "Valorizzare il campo indirizzo")
    @ApiModelProperty(required = true)
    private IndirizzoDTO indirizzo;

}