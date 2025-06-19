package dk.capworld.cinemautils.controller;


import dk.capworld.cinemautils.domain.Shows;
import dk.capworld.cinemautils.model.BookingResult;
import dk.capworld.cinemautils.service.ShowsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CinemaBookingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ENTITY_NAME = "cinema_booking";

    private final ShowsService showsService;

    public CinemaBookingController(ShowsService showsService) {
        this.showsService = showsService;
    }

    @GetMapping("/findAllShows")
    public List<Shows> findAllShows() {
        logger.info("findAllShows() called.");
        return this.showsService.findAllShows();
    }

    @GetMapping("/findAllShowsAndAvailableSeatings")
    public ResponseEntity<List<BookingResult>>  findAllShowsAndAvailableSeatings() {
        logger.info("findAllShowsAndAvailableSeatings() called.");
        return new ResponseEntity<>(this.showsService.findAllShowsAndAvailableSeatings(), HttpStatus.OK);
    }


    // Update
//            return  ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoice.getInvoiceHeader().getSystemDocNumber().toString())).body(updatedInvoice);
}
