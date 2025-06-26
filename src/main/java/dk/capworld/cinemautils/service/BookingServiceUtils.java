package dk.capworld.cinemautils.service;

import dk.capworld.cinemautils.domain.SeatReservations;

import java.util.ArrayList;
import java.util.List;

import static dk.capworld.cinemautils.service.BookingService.SEATS_IN_CINEMA;

public class BookingServiceUtils {

    public static List<Integer> getAvailableSeatsFromReservations(List<SeatReservations> reservations) {
        ArrayList<Integer> reservedSeats = new ArrayList<>();
        for (SeatReservations seatReservations : reservations ) {
            reservedSeats.add(seatReservations.getSeat());
        }

        ArrayList<Integer> availableSeats = new ArrayList<>();
        for (int i = 1;i <= SEATS_IN_CINEMA; i++) {
            if (!reservedSeats.contains(i)) {
                availableSeats.add(i);
            }
        }
        return availableSeats;
    }

}
