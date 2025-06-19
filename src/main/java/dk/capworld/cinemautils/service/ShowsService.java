package dk.capworld.cinemautils.service;

import dk.capworld.cinemautils.domain.SeatingReservations;
import dk.capworld.cinemautils.domain.Shows;
import dk.capworld.cinemautils.model.BookingResult;
import dk.capworld.cinemautils.repository.SeatingReservationsRepository;
import dk.capworld.cinemautils.repository.ShowsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowsService {

    private static final Integer SEATS_IN_CINEMA = 50;

    private static final Logger logger = LoggerFactory.getLogger(ShowsService.class);

    private final SeatingReservationsRepository seatingReservationsRepository;
    private final ShowsRepository showsRepository;

    public ShowsService(SeatingReservationsRepository seatingReservationsRepository,
                        ShowsRepository showsRepository) {
        this.seatingReservationsRepository = seatingReservationsRepository;
        this.showsRepository = showsRepository;
    }

    public List<Shows> findAllShows () {
        List<Shows> allShows = this.showsRepository.findAll();
        return allShows;
    }

    public List<BookingResult> findAllShowsAndAvailableSeatings () {
        ArrayList<BookingResult> bookingResults = new ArrayList<>();
        List<Shows> allShows = this.showsRepository.findAll();
        for (Shows show: allShows) {
            BookingResult bookingResult = BookingResult.builder()
                    .id(show.getId())
                    .movieName(show.getMovieName())
                    .runningDate(show.getRunningDate())
                    .build();
            List<SeatingReservations> reservations = this.seatingReservationsRepository.findAllByShow(show);
            List<Integer> availableSeats = getAvailableSeatsFromReservations(reservations);
            bookingResult.setAvailableSeats(availableSeats);
            bookingResults.add(bookingResult);
        }
        return bookingResults;
    }


    private List<Integer> getAvailableSeatsFromReservations(List<SeatingReservations> reservations) {
        ArrayList<Integer> reservedSeats = new ArrayList<>();
        for (SeatingReservations seatingReservations : reservations ) {
            reservedSeats.add(seatingReservations.getSeating());
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
