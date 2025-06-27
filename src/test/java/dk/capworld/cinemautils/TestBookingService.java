package dk.capworld.cinemautils;

import dk.capworld.cinemautils.domain.SeatReservations;
import dk.capworld.cinemautils.domain.Shows;
import dk.capworld.cinemautils.service.BookingServiceUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static dk.capworld.cinemautils.service.BookingService.SEATS_IN_CINEMA;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestBookingService {

    @Test
    public void getAvailableSeatsFromReservations() throws Exception {
        Shows show = new Shows();
        show.setId(1L);
        show.setMovieName("TopGun 2");
        show.setRunningDate(LocalDateTime.now());

        List<SeatReservations> reservations = new ArrayList<>(List.of(new SeatReservations(show, 1), new SeatReservations(show, 2), new SeatReservations(show, 3)));

        List<Integer> availableSeats = BookingServiceUtils.getAvailableSeatsFromReservations(reservations);

        assertEquals(SEATS_IN_CINEMA - 3, availableSeats.size());
    }

    @Test
    public void getAvailableSeatsFromReservationsFull() throws Exception {
        Shows show = new Shows();
        show.setId(1L);
        show.setMovieName("TopGun 2");
        show.setRunningDate(LocalDateTime.now());

        List<SeatReservations> reservations = new ArrayList<>();
        for (int i=1;i <= SEATS_IN_CINEMA;i++) {
            reservations.add(new SeatReservations(show, i));
        }
        List<Integer> availableSeats = BookingServiceUtils.getAvailableSeatsFromReservations(reservations);

        assertEquals(0, availableSeats.size());
    }


    @Test
    public void getAvailableSeatsFromReservationsOverrun() throws Exception {
        Shows show = new Shows();
        show.setId(1L);
        show.setMovieName("TopGun 2");
        show.setRunningDate(LocalDateTime.now());

        List<SeatReservations> reservations = new ArrayList<>();
        for (int i=1;i <= SEATS_IN_CINEMA + 10;i++) {
            reservations.add(new SeatReservations(show, i));
        }
        List<Integer> availableSeats = BookingServiceUtils.getAvailableSeatsFromReservations(reservations);

        assertEquals(0, availableSeats.size());
    }

}
