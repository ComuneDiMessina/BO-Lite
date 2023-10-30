package it.almaviva.impleme.bolite.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.impleme.bolite.integration.entities.booking.BookingEntity;
import it.almaviva.impleme.bolite.integration.entities.room.RoomReservationEntity;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;

public class BoliteUtils {

	public static String readFileToBase64(String path) throws IOException {

		File file = new File(path);
		String encoded = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
		return encoded;
	}

	public static String formatJSON(Object object) {
		String json = new String();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonObj = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			jsonObj = new JSONObject(jsonObj).toString(4);
			json = "\n\n" + jsonObj;
		} catch (Exception e) {
			json = object.toString();
		}
		return json;
	}

	public static boolean overlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2){
		return start1.isBefore(end2) && end1.isAfter(start2);
	}
	
	public static boolean overlapDate(LocalDate dayStart, LocalDate dayEnd, LocalTime hourStart, LocalTime hourEnd,
			RoomReservationEntity r) {

		if ((((dayStart.isAfter(r.getGiornoDa()) && dayStart.isBefore(r.getGiornoA()))
				|| dayStart.isEqual(r.getGiornoDa()))
				|| ((dayEnd.isBefore(r.getGiornoA()) && dayEnd.isAfter(r.getGiornoDa()))
						|| dayEnd.isEqual(r.getGiornoA())))
				|| (dayStart.isBefore(r.getGiornoDa()) && dayEnd.isAfter(r.getGiornoA()))) {
			if ((((hourStart.isAfter(r.getOraDa()) && hourStart.isBefore(r.getOraA()))
					|| hourStart.equals(r.getOraDa()))
					|| ((hourEnd.isBefore(r.getOraA()) && hourEnd.isAfter(r.getOraDa()))
							|| hourEnd.equals(r.getOraA())))
					|| (hourStart.isBefore(r.getOraDa()) && hourEnd.isAfter(r.getOraA()))) {
				return true;
			}
		}
		return false;

	}
	
	public static boolean overlapDate(LocalDate dayStart, LocalDate dayEnd, LocalTime hourStart, LocalTime hourEnd,
			BookingEntity b) {

		if ((((dayStart.isAfter(b.getBookingStartDate()) && dayStart.isBefore(b.getBookingEndDate()))
				|| dayStart.isEqual(b.getBookingStartDate()))
				|| ((dayEnd.isBefore(b.getBookingEndDate()) && dayEnd.isAfter(b.getBookingStartDate()))
						|| dayEnd.isEqual(b.getBookingEndDate())))
				|| (dayStart.isBefore(b.getBookingStartDate()) && dayEnd.isAfter(b.getBookingEndDate()))) {
			if ((((hourStart.isAfter(b.getBookingStartHour()) && hourStart.isBefore(b.getBookingEndHour()))
					|| hourStart.equals(b.getBookingStartHour()))
					|| ((hourEnd.isBefore(b.getBookingEndHour()) && hourEnd.isAfter(b.getBookingStartHour()))
							|| hourEnd.equals(b.getBookingEndHour())))
					|| (hourStart.isBefore(b.getBookingStartHour()) && hourEnd.isAfter(b.getBookingEndHour()))) {
				return true;
			}
		}
		return false;

	}

	public static boolean overlapOnlyDate(LocalDate dayStart, LocalDate dayEnd,
																		BookingEntity b) {

		if ((((dayStart.isAfter(b.getBookingStartDate()) && dayStart.isBefore(b.getBookingEndDate()))
						|| dayStart.isEqual(b.getBookingStartDate()))
						|| ((dayEnd.isBefore(b.getBookingEndDate()) && dayEnd.isAfter(b.getBookingStartDate()))
						|| dayEnd.isEqual(b.getBookingEndDate())))
						|| (dayStart.isBefore(b.getBookingStartDate()) && dayEnd.isAfter(b.getBookingEndDate()))) {
						return true;

		}
		return false;

	}
	
	public static boolean checkBookingActivation(LocalDate dayStart, LocalTime hourStart, LocalTime hourEnd,
			BookingEntity b) {

		if ((((dayStart.isAfter(b.getBookingStartDate()) && dayStart.isBefore(b.getBookingEndDate()))
				|| dayStart.isEqual(b.getBookingStartDate()) || dayStart.isEqual(b.getBookingEndDate())))) {
			if ((((hourStart.isAfter(b.getBookingStartHour()) && hourStart.isBefore(b.getBookingEndHour()))
					|| hourStart.equals(b.getBookingStartHour()) || hourStart.equals(b.getBookingEndHour())))) {
				return true;
			}
		}
		return false;

	}

}
