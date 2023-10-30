package it.almaviva.impleme.bolite.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookingDTO {
	@JsonProperty("bookingEnd")
	private String bookingEnd;
	@JsonProperty("bookingStart")
	private String bookingStart;
}
