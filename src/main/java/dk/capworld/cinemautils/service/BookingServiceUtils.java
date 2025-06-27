package dk.capworld.cinemautils.service;

import dk.capworld.cinemautils.domain.SeatReservations;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static dk.capworld.cinemautils.service.BookingService.SEATS_IN_CINEMA;

public class BookingServiceUtils {

    public static List<Integer> getAvailableSeatsFromReservations(List<SeatReservations> reservations) {
        Set<Integer> reservedSeats = reservations.stream()
                .map(SeatReservations::getSeat)
                .collect(Collectors.toSet());

        return IntStream.rangeClosed(1, SEATS_IN_CINEMA)
                .filter(seat -> !reservedSeats.contains(seat))
                .boxed()
                .toList();
    }
}
