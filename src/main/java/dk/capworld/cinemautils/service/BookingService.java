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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private static final Integer SEATS_IN_CINEMA = 50;

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final SeatReservationsRepository seatReservationsRepository;
    private final ShowsRepository showsRepository;

    public BookingService(SeatReservationsRepository seatReservationsRepository,
                          ShowsRepository showsRepository) {
        this.seatReservationsRepository = seatReservationsRepository;
        this.showsRepository = showsRepository;
    }

    public List<Shows> findAllShows () {
        List<Shows> allShows = this.showsRepository.findAll();
        return allShows;
    }

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
        logger.info("Make reservation on movie " + bookingRequest.getShowId() + " on seat(s): " + bookingRequest.getSeats().toString() );
        Shows show = showsRepository.findById(bookingRequest.getShowId()).orElseThrow(() -> new BookingException(BookingException.ErrorCode.ObjectNotFound, "Movie with id " + bookingRequest.getShowId() + " could not be found"));

        List<Integer> seatsToBook = bookingRequest.getSeats();
        for (Integer seat : seatsToBook) {
            if (isAlreadyBooked(show, seat)) {
                throw new BookingException(BookingException.ErrorCode.ObjectCannotBeSaved, "Seat " + seat + " is already booked for movie " + bookingRequest.getShowId());
            }
            saveReservation(show, seat);
        }
        return buildBookingResult(show);
    }

    private Boolean isAlreadyBooked(Shows show, Integer seat) {
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
        logger.info("Cancel reservation on movie " + bookingRequest.getShowId() + " on seat(s): " + bookingRequest.getSeats().toString() );
        Shows show = showsRepository.findById(bookingRequest.getShowId()).orElseThrow(() -> new BookingException(BookingException.ErrorCode.ObjectNotFound, "Movie could not be found"));
        if (bookingRequest.getSeats().isEmpty()) {
            logger.info("No seats to cancel for movie: " + bookingRequest.getShowId());
        }
        for (Integer seat : bookingRequest.getSeats()) {
            cancelReservation(show, seat);
        }
        return buildBookingResult(show);
    }


    public void saveReservation(Shows show, Integer seat) {
        seatReservationsRepository.save(new SeatReservations(show, seat, new Date()));
    }

    public void cancelReservation(Shows show, Integer seat) {
        SeatReservations seatReservation = seatReservationsRepository.findByShowAndSeat(show, seat).orElseThrow(() -> new BookingException(BookingException.ErrorCode.ObjectNotFound, "Booking with seat " + seat + " could not be found"));
        seatReservationsRepository.deleteById(seatReservation.getId());
    }


    private List<Integer> getAvailableSeatsFromReservations(List<SeatReservations> reservations) {
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


    private BookingResult buildBookingResult(Shows show) {
        BookingResult bookingResult = BookingResult.builder()
                .id(show.getId())
                .movieName(show.getMovieName())
                .runningDate(show.getRunningDate())
                .build();
        List<SeatReservations> reservations = this.seatReservationsRepository.findAllByShow(show);
        List<Integer> availableSeats = getAvailableSeatsFromReservations(reservations);
        bookingResult.setAvailableSeats(availableSeats);

        return bookingResult;
    }
}
