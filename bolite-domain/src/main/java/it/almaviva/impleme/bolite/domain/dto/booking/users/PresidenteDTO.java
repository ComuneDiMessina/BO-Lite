package it.almaviva.impleme.bolite.domain.dto.booking.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
@Valid
@ApiModel(value = "Presidente")
public class PresidenteDTO {

    @NotBlank(message = "Valorizzare il campo nome")
    @ApiModelProperty( example = "Francesco", required = true)
    private String nome;

    @NotBlank(message = "Valorizzare il campo cognome")
    @ApiModelProperty(example = "Bianchi", required = true)
    private String cognome;

    @NotBlank(message = "Valorizzare il campo codiFisc")
    @ApiModelProperty(example = "IT123456789", required = true)
    private String codiFisc;

    @NotBlank(message = "Valorizzare il campo email")
    @ApiModelProperty(example = "f.bianchi@gmail.com", required = true)
    private String email;

    @NotBlank(message = "Valorizzare il campo telefono")
    @ApiModelProperty(example = "(+39)3450000000", dataType = "string", required = true)
    private String telefono;

    @ApiModelProperty(example = "1930-01-01", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @NotNull(message = "Valorizzare il campo dataNascita")
    private LocalDate dataNascita;

    @NotBlank(message = "Valorizzare il campo luogoNascita")
    @ApiModelProperty(example = "Roma", required = true)
    private String luogoNascita;
}
