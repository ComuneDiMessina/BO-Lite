package it.almaviva.impleme.bolite.integration.model.roombooking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomBookingResponse{

  @JsonProperty(value = "_bookingId")
  private String bookingId;
  private Date bookingStart;
  private Date bookingEnd;
  private Long startHour;
  private Long duration;
  private String state;
  private ArrayList<String>[] recurring;
  private String businessUnit;
  private String purpose;
}