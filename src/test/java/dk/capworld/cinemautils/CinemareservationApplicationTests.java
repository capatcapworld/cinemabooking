package dk.capworld.cinemautils;

import dk.capworld.cinemautils.domain.Shows;
import dk.capworld.cinemautils.exceptions.BookingException;
import dk.capworld.cinemautils.model.BookingRequest;
import dk.capworld.cinemautils.model.BookingResult;
import dk.capworld.cinemautils.repository.ShowsRepository;
import dk.capworld.cinemautils.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static dk.capworld.cinemautils.service.BookingService.SEATS_IN_CINEMA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class CinemareservationApplicationTests {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ShowsRepository showsRepository;


    /**
     * Precondition is that show with id 5 must exist in table shows
     */
	@Test
	void testBookingServiceMakeReservation() {
        Optional<Shows> show = showsRepository.findById(5L);
        if (show.isEmpty()) {
            fail("Show with 5 is not set up as precondition.");
        }

        if (bookingService.isAlreadyBooked(show.get(), 1)) {
            bookingService.cancelReservation(show.get(), 1);
        }
        if (bookingService.isAlreadyBooked(show.get(), 2)) {
            bookingService.cancelReservation(show.get(), 2);
        }
        if (bookingService.isAlreadyBooked(show.get(), 3)) {
            bookingService.cancelReservation(show.get(), 3);
        }

        List<Integer> reserveSeats = new ArrayList<>(Arrays.asList(1,2,3));
        BookingRequest bookingRequest = BookingRequest.builder()
                .showId(5L)
                .seats(reserveSeats)
                .build();
        bookingService.makeReservations(bookingRequest);

        List<BookingResult> bookingResults = bookingService.findAllShowsAndAvailableSeats();
        List<BookingResult> filteredBookingResults = bookingResults.stream()
                        .filter(b -> b.getId().equals(5L))
                        .toList();

        assertEquals(1, filteredBookingResults.size());
        assertEquals(SEATS_IN_CINEMA-3, filteredBookingResults.getFirst().getAvailableSeats().size());

        bookingService.cancelReservations(bookingRequest);

        if (bookingService.isAlreadyBooked(show.get(), 1)) {
            fail("Seat 1 at show 3 is not cancelled correctly.");
        }
        if (bookingService.isAlreadyBooked(show.get(), 2)) {
            fail("Seat 2 at show 3 is not cancelled correctly.");
        }
        if (bookingService.isAlreadyBooked(show.get(), 3)) {
            fail("Seat 3 at show 3 is not cancelled correctly.");
        }

    }


    /**
     * Precondition is that show with id 5 must exist in table shows
     */
    @Test
    void testBookingServiceMakeReservationAlreadyBooked() {
        Optional<Shows> show = showsRepository.findById(5L);
        if (show.isEmpty()) {
            fail("Show with 5 is not set up as precondition.");
        }

        if (bookingService.isAlreadyBooked(show.get(), 1)) {
            bookingService.cancelReservation(show.get(), 1);
        }
        if (bookingService.isAlreadyBooked(show.get(), 2)) {
            bookingService.cancelReservation(show.get(), 2);
        }
        if (bookingService.isAlreadyBooked(show.get(), 3)) {
            bookingService.cancelReservation(show.get(), 3);
        }

        List<Integer> reserveSeats = new ArrayList<>(Arrays.asList(1,2,3));
        BookingRequest bookingRequest = BookingRequest.builder()
                .showId(5L)
                .seats(reserveSeats)
                .build();
        bookingService.makeReservations(bookingRequest);

        List<Integer> reserveSeatsAlredyDone = new ArrayList<>(Arrays.asList(3));
        BookingRequest bookingRequestAlreadyDone = BookingRequest.builder()
                .showId(5L)
                .seats(reserveSeatsAlredyDone)
                .build();
        try {
            bookingService.makeReservations(bookingRequestAlreadyDone);
        } catch (BookingException ex) {
            assertEquals(BookingException.ErrorCode.OBJECT_CANNOT_BE_SAVED, ex.getErrorCode());
        }

        List<BookingResult> bookingResults = bookingService.findAllShowsAndAvailableSeats();
        List<BookingResult> filteredBookingResults = bookingResults.stream()
                .filter(b -> b.getId().equals(5L))
                .toList();

        assertEquals(1, filteredBookingResults.size());
        assertEquals(SEATS_IN_CINEMA-3, filteredBookingResults.getFirst().getAvailableSeats().size());

        bookingService.cancelReservations(bookingRequest);


    }

}
