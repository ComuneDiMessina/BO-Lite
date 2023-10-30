package it.almaviva.impleme.bolite.core.room;

import it.almaviva.impleme.bolite.domain.dto.booking.books.EventDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.reservation.NewReservationDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.*;
import it.almaviva.impleme.bolite.domain.dto.booking.services.NewServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.UpdateServiceDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IRoomService {
	
	RoomDTO createRoom(NewRoomDTO roomDTO, String ente);

	void deleteRoom(UUID id);

	void deleteServiceOfRoom(UUID roomId, Integer serviceId);

	void deleteReservationOfRoom(UUID roomId, Integer reservationId);

	RoomDTO getRoom(UUID id);

	List<RoomDTO> getRooms(String comune, Integer tipologia, Integer categoria);

	RoomDTO createServizio(UUID id, NewServiceDTO body);

	RoomDTO createReservation(UUID id, NewReservationDTO body);

	RoomDTO createTariffario(UUID id, NewTariffarioDTO body);

	void deleteTariffario(UUID roomId, Integer tariffId);

	List<CategoryDTO> getCategorie();

	void deleteCategoria(Integer id);

	List<TypologyDTO> getTypologies();

	void deleteTipologiaStruttura(Integer id);

	AvailabilityDTO verificaDisponibilita(UUID roomId, LocalDate dayStart, LocalDate dayEnd, Integer tariffa);

	AvailabilityDTO verificaDisponibilitaSmart(UUID roomId, LocalDate dayStart, LocalDate dayEnd);

    TypologyDTO createTypology(NewTypologyDTO body);

	CategoryDTO createCategory(NewCategoryDTO body);

    List<RoomDTO> getRoomsForAdmin(Integer tipologia, Integer categoria, String ente);

	RoomDTO updateRoom(UUID roomId, UpdateRoomDTO body);

	TariffarioDTO updateTariffario(UUID roomId, Integer tariffId, UpdateTariffarioDTO body);

	List<EventDTO> getTipologieEvento(UUID roomId);

	void deleteGiorno(UUID roomId, Integer tariffId, Integer cfId);

	void deleteFascia(UUID roomId, Integer tariffId, Integer cfId, Integer id);

	ServiceDTO updateServiceOfRoom(UUID roomId, Integer serviceId, UpdateServiceDTO body);

	TariffarioDTO updateFascia(UUID roomId, Integer tariffId, Integer cfId, Integer id, UpdateFasciaDTO body);

	void deleteApertura(UUID roomId, Integer id);

	RoomDTO createApertura(UUID roomId, NewAperturaDTO body);

	RoomDTO updateApertura(UUID roomId, Integer id, UpdateAperturaDTO body);

	TariffarioDTO crateGiorno(UUID roomId, Integer tariffId, NewRangeDTO body);
}
