package dk.capworld.cinemautils;

import dk.capworld.cinemautils.domain.SeatReservations;
import dk.capworld.cinemautils.domain.Shows;
import dk.capworld.cinemautils.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static dk.capworld.cinemautils.service.BookingService.SEATS_IN_CINEMA;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class TestBookingService {

    //@Autowired
    @InjectMocks
    private BookingService bookingService;

    @Test
    public void getAvailableSeatsFromReservations() throws Exception {
        Shows show = new Shows();
        show.setId(1L);
        show.setMovieName("TopGun 2");
        show.setRunningDate(new Date());
        show.setCreated(new Date());

        List<SeatReservations> reservations = new ArrayList<>(List.of(new SeatReservations(show, 1, new Date()), new SeatReservations(show, 2, new Date()), new SeatReservations(show, 3, new Date())));

        Method method = BookingService.class.getDeclaredMethod("getAvailableSeatsFromReservations", List.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> availableSeats = (List<Integer>) method.invoke(bookingService, reservations);

        assertEquals(SEATS_IN_CINEMA - 3, availableSeats.size());
    }

    @Test
    public void getAvailableSeatsFromReservationsFull() throws Exception {
        Shows show = new Shows();
        show.setId(1L);
        show.setMovieName("TopGun 2");
        show.setRunningDate(new Date());
        show.setCreated(new Date());

        List<SeatReservations> reservations = new ArrayList<>();
        for (int i=1;i <= SEATS_IN_CINEMA;i++) {
            reservations.add(new SeatReservations(show, i, new Date()));
        }

        Method method = BookingService.class.getDeclaredMethod("getAvailableSeatsFromReservations", List.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> availableSeats = (List<Integer>) method.invoke(bookingService, reservations);

        assertEquals(0, availableSeats.size());
    }


    @Test
    public void getAvailableSeatsFromReservationsOverrun() throws Exception {
        Shows show = new Shows();
        show.setId(1L);
        show.setMovieName("TopGun 2");
        show.setRunningDate(new Date());
        show.setCreated(new Date());

        List<SeatReservations> reservations = new ArrayList<>();
        for (int i=1;i <= SEATS_IN_CINEMA + 10;i++) {
            reservations.add(new SeatReservations(show, i, new Date()));
        }

        Method method = BookingService.class.getDeclaredMethod("getAvailableSeatsFromReservations", List.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> availableSeats = (List<Integer>) method.invoke(bookingService, reservations);

        assertEquals(0, availableSeats.size());
    }

}
