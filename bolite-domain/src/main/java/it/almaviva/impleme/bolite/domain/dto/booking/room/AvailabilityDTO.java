package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@Valid
@ApiModel(value = "Availability")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailabilityDTO {

    private List<AvailabilityDayDTO> giorni = new ArrayList<>(1);

}
