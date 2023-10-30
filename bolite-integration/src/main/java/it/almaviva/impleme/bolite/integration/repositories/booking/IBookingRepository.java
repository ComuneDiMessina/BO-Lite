package it.almaviva.impleme.bolite.integration.repositories.booking;


import it.almaviva.impleme.bolite.integration.entities.booking.BookingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface IBookingRepository  extends CrudRepository<BookingEntity, UUID> {
	
	List<BookingEntity> findByRoomId(Integer roomId);
}
