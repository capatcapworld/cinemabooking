package dk.capworld.cinemautils.controller;


import dk.capworld.cinemautils.domain.Shows;
import dk.capworld.cinemautils.model.BookingRequest;
import dk.capworld.cinemautils.model.BookingResult;
import dk.capworld.cinemautils.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CinemaBookingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ENTITY_NAME = "cinema_booking";

    private final BookingService bookingService;

    public CinemaBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/findAllShows")
    public List<Shows> findAllShows() {
        logger.info("findAllShows() called.");
        return this.bookingService.findAllShows();
    }

    @GetMapping("/findAllShowsAndAvailableSeatings")
    public ResponseEntity<List<BookingResult>>  findAllShowsAndAvailableSeatings() {
        logger.info("findAllShowsAndAvailableSeatings() called.");
        return new ResponseEntity<>(this.bookingService.findAllShowsAndAvailableSeats(), HttpStatus.OK);
    }

    @PostMapping("/createNewReservations")
    public ResponseEntity<BookingResult> createNewReservations(@RequestBody BookingRequest bookingRequest) throws Exception {
        logger.info("createNewReservations() called.");
        return new ResponseEntity<>(this.bookingService.makeReservations(bookingRequest), HttpStatus.OK);
    }

    @DeleteMapping("/cancelReservations")
    public ResponseEntity<BookingResult> cancelReservation(@RequestBody BookingRequest bookingRequest) throws Exception {
        logger.info("cancelReservations() called.");
        return new ResponseEntity<>(this.bookingService.cancelReservations(bookingRequest), HttpStatus.OK);
    }
    // Update
//            return  ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoice.getInvoiceHeader().getSystemDocNumber().toString())).body(updatedInvoice);
}
