package it.almaviva.impleme.bolite.core.booking;

import it.almaviva.impleme.bolite.domain.dto.booking.DiscardDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.BookingDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.NewBookingDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.StatoDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.OpenDoorDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface IBookingService {

    BookingDTO createRoomBooking(NewBookingDTO body);

    BookingDTO getRoomBookingCaseFile(UUID idCaseFile);

    List<BookingDTO> getMiePrenotazioni(String cf, String enteId, Integer statoId, UUID roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd);

    List<StatoDTO> getStati();

    List<BookingDTO> filterBookings(String codiFisc, Integer stato, UUID roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd, String ente, String tributo);

    byte[] qrCodeGenerator(UUID idCaseFile);

    void discardPratica(UUID idCaseFile, DiscardDTO motivo);

    BigDecimal verificaPrezzo(UUID roomId, Integer eventId, List<Integer> services, LocalDate dayStart, LocalDate dayEnd, LocalTime hourStart, LocalTime hourEnd, Boolean interaGiornata);
    
    void OpenDoor(OpenDoorDTO body) throws Exception;
}
