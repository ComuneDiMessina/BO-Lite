package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Valid
@ApiModel
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NewAperturaDTO {


    @Getter
    public enum DAYS {
        @JsonProperty("LUN")
        LUN( 1),

        @JsonProperty("MAR")
        MAR( 2),

        @JsonProperty("MER")
        MER( 3),

        @JsonProperty("GIO")
        GIO( 4),

        @JsonProperty("VEN")
        VEN( 5),

        @JsonProperty("SAB")
        SAB( 6),

        @JsonProperty("DOM")
        DOM( 7);

        private final int meters;

        private static Map map = new HashMap<>();

        DAYS(int meters) {
            this.meters = meters;
        }

        static {
            for (DAYS pageType : DAYS.values()) {
                map.put(pageType.meters, pageType);
            }
        }

        public static DAYS valueOf(int pageType) {
            return (DAYS) map.get(pageType);
        }

    }

    @NotNull(message = "Valorizzare il campo giorno")
    @JsonProperty(required = true)
    @EqualsAndHashCode.Include
    @ApiModelProperty(required = true, example = "LUN", notes = "Giorno della settimana etichetta" , allowableValues = "LUN,MAR,MER,GIO,VEN,SAB,DOM")
    private DAYS giorno;

    @NotNull(message = "Valorizzare il campo oraDa")
    @ApiModelProperty( required = true, example = "09:00:00")
    @JsonProperty(value = "oraDa", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime oraDa;

    @NotNull(message = "Valorizzare il campo oraA")
    @ApiModelProperty(position = 1, required = true, example = "21:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty(value = "oraA", required = true)
    private LocalTime oraA;

}
