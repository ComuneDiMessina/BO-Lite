package it.almaviva.impleme.bolite.domain.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomListDTO {

    private List<RoomDTO> rooms;
}
