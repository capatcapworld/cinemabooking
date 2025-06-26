package dk.capworld.cinemautils.service;

import dk.capworld.cinemautils.domain.SeatReservations;
import dk.capworld.cinemautils.domain.Shows;
import dk.capworld.cinemautils.exceptions.BookingException;
import dk.capworld.cinemautils.model.BookingRequest;
import dk.capworld.cinemautils.model.BookingResult;
import dk.capworld.cinemautils.repository.SeatReservationsRepository;
import dk.capworld.cinemautils.repository.ShowsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    public static final Integer SEATS_IN_CINEMA = 50;

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final SeatReservationsRepository seatReservationsRepository;
    private final ShowsRepository showsRepository;

    public BookingService(SeatReservationsRepository seatReservationsRepository,
                          ShowsRepository showsRepository) {
        this.seatReservationsRepository = seatReservationsRepository;
        this.showsRepository = showsRepository;
    }

    public List<Shows> findAllShows () {
        return this.showsRepository.findAll();
    }

    /**
     *  Calculated all available seats for all shows
     *
     * @return List of all available seats for all shows
     */
    public List<BookingResult> findAllShowsAndAvailableSeats() {
        ArrayList<BookingResult> bookingResults = new ArrayList<>();
        List<Shows> allShows = this.showsRepository.findAll();
        for (Shows show: allShows) {
            bookingResults.add(buildBookingResult(show));
        }
        return bookingResults;
    }


    /**
     * This method makes a reservation of seats for the movie reference in @bookingRequest.
     *
     * @param bookingRequest The seats to book
     * @return The booked seats of the movie.
     */
    @Transactional
    public BookingResult makeReservations(BookingRequest bookingRequest) throws BookingException {
        logger.info("Make reservation on movie {} on seat(s): {}", bookingRequest.showId(), bookingRequest.seats().toString());
        Shows show = showsRepository.findById(bookingRequest.showId()).orElseThrow(() -> new BookingException(BookingException.ErrorCode.OBJECT_NOT_FOUND, "Movie with id " + bookingRequest.showId() + " could not be found"));

        List<Integer> seatsToBook = bookingRequest.seats();
        for (Integer seat : seatsToBook) {
            if (isAlreadyBooked(show, seat)) {
                throw new BookingException(BookingException.ErrorCode.OBJECT_CANNOT_BE_SAVED, "Seat " + seat + " is already booked for movie " + bookingRequest.showId());
            }
            saveReservation(show, seat);
        }
        return buildBookingResult(show);
    }

    public Boolean isAlreadyBooked(Shows show, Integer seat) {
        return seatReservationsRepository.findByShowAndSeat(show, seat).isPresent();
    }

    /**
     * This method cancel one or more reservations of seats for the movie reference in @bookingRequest.
     *
     * @param bookingRequest The seats to cancel
     * @return The booked seats of the movie.
     */
    @Transactional
    public BookingResult cancelReservations(BookingRequest bookingRequest) throws BookingException {
        logger.info("Cancel reservation on movie " + bookingRequest.showId() + " on seat(s): " + bookingRequest.seats().toString() );
        Shows show = showsRepository.findById(bookingRequest.showId()).orElseThrow(() -> new BookingException(BookingException.ErrorCode.OBJECT_NOT_FOUND, "Movie could not be found"));
        if (bookingRequest.seats().isEmpty()) {
            logger.info("No seats to cancel for movie: {}", bookingRequest.showId());
        }
        for (Integer seat : bookingRequest.seats()) {
            cancelReservation(show, seat);
        }
        return buildBookingResult(show);
    }


    public void saveReservation(Shows show, Integer seat) {
        seatReservationsRepository.save(new SeatReservations(show, seat));
    }

    public void cancelReservation(Shows show, Integer seat) {
        SeatReservations seatReservation = seatReservationsRepository.findByShowAndSeat(show, seat).orElseThrow(() -> new BookingException(BookingException.ErrorCode.OBJECT_NOT_FOUND, "Booking with seat " + seat + " could not be found"));
        seatReservationsRepository.deleteById(seatReservation.getId());
    }

    private BookingResult buildBookingResult(Shows show) {
        List<SeatReservations> reservations = this.seatReservationsRepository.findAllByShow(show);
        List<Integer> availableSeats = BookingServiceUtils.getAvailableSeatsFromReservations(reservations);
        return BookingResult.builder()
                .id(show.getId())
                .movieName(show.getMovieName())
                .runningDate(show.getRunningDate())
                .availableSeats(availableSeats)
                .build();
    }
}
