package dk.capworld.cinemautils.controller;


import dk.capworld.cinemautils.domain.Shows;
import dk.capworld.cinemautils.model.BookingRequest;
import dk.capworld.cinemautils.model.BookingResult;
import dk.capworld.cinemautils.service.BookingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class CinemaBookingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ENTITY_NAME = "cinema_booking";

    private final BookingService bookingService;

    public CinemaBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * THis endpoint will return a list of a available shows
     *
     * @return The list of shows
     */
    @GetMapping("/findAllShows")
    public List<Shows> findAllShows() {
        logger.info("findAllShows() called.");
        return this.bookingService.findAllShows();
    }

    /**
     * This method will return all available shows and for each show is shown available seats
     *
     * @return The list of shows with available seats
     */
    @GetMapping("/findAllShowsAndAvailableSeatings")
    public ResponseEntity<List<BookingResult>>  findAllShowsAndAvailableSeatings() {
        logger.info("findAllShowsAndAvailableSeatings() called.");
        return new ResponseEntity<>(this.bookingService.findAllShowsAndAvailableSeats(), HttpStatus.OK);
    }

    /**
     * Create reservation on a specific show.
     *
     * @param bookingRequest Information about the show and seats that should be booked.
     * @return The list of available seats for the show being referenced.
     */
    @PostMapping("/createNewReservations")
    public ResponseEntity<BookingResult> createNewReservations(@RequestBody @Valid BookingRequest bookingRequest) {
        logger.info("createNewReservations() called.");
        return new ResponseEntity<>(this.bookingService.makeReservations(bookingRequest), HttpStatus.OK);
    }

    /**
     * Cancel specific reservation on a show
     *
     * @param bookingRequest Information about the show and seats that should be cancelled
     * @return The list of available seats for the show being referenced.
     */
    @DeleteMapping("/cancelReservations")
    public ResponseEntity<BookingResult> cancelReservation(@RequestBody BookingRequest bookingRequest) {
        logger.info("cancelReservations() called.");
        return new ResponseEntity<>(this.bookingService.cancelReservations(bookingRequest), HttpStatus.OK);
    }

}
