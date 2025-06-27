package dk.capworld.cinemautils;

import dk.capworld.cinemautils.domain.SeatReservations;
import dk.capworld.cinemautils.domain.Shows;
import dk.capworld.cinemautils.exceptions.BookingException;
import dk.capworld.cinemautils.model.BookingRequest;
import dk.capworld.cinemautils.model.BookingResult;
import dk.capworld.cinemautils.repository.SeatReservationsRepository;
import dk.capworld.cinemautils.repository.ShowsRepository;
import dk.capworld.cinemautils.service.BookingService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static dk.capworld.cinemautils.service.BookingService.SEATS_IN_CINEMA;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest
class CinemareservationApplicationTests {

    // In memory postgresql database / TestContainer
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ShowsRepository showsRepository;

    @Autowired
    private SeatReservationsRepository seatReservationsRepository;


    @BeforeEach
    public void addBeforeEach() {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE shows RESTART IDENTITY CASCADE");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Shows shows1 = new Shows();
        shows1.setMovieName("TopGun");
        shows1.setRunningDate(LocalDateTime.parse("2026-06-10T19:30:00"));
        showsRepository.save(shows1);
        Shows shows2 = new Shows();
        shows2.setMovieName("TopGun");
        shows2.setRunningDate(LocalDateTime.parse("2026-06-10T22:30:00"));
        showsRepository.save(shows2);
        Shows shows3 = new Shows();
        shows3.setMovieName("TopGun");
        shows3.setRunningDate(LocalDateTime.parse("2026-06-11T16:30:00"));
        showsRepository.save(shows3);
        Shows shows4 = new Shows();
        shows4.setMovieName("TopGun");
        shows4.setRunningDate(LocalDateTime.parse("2026-06-11T19:30:00"));
        showsRepository.save(shows4);
        Shows shows5 = new Shows();
        shows5.setMovieName("TopGun");
        shows5.setRunningDate(LocalDateTime.parse("2026-06-11T22:30:00"));
        showsRepository.save(shows5);

        Shows managedShow2 = showsRepository.findById(shows2.getId()).orElseThrow();
        SeatReservations seatReservations1 = new SeatReservations(managedShow2, 44);
        seatReservationsRepository.save(seatReservations1);
        Shows managedShow3 = showsRepository.findById(shows3.getId()).orElseThrow();
        SeatReservations seatReservations2 = new SeatReservations(managedShow3, 20);
        seatReservationsRepository.save(seatReservations2);
        SeatReservations seatReservations3 = new SeatReservations(managedShow3, 30);
        seatReservationsRepository.save(seatReservations3);
    }


    @Transactional
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
                        .filter(b -> b.id().equals(5L))
                        .toList();

        assertEquals(1, filteredBookingResults.size());
        assertEquals(SEATS_IN_CINEMA-3, filteredBookingResults.getFirst().availableSeats().size());

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


    @Transactional
    @Test
    void testBookingServiceMakeReservationAlreadyBooked() {
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

        BookingException bookingException = assertThrows(BookingException.class, () -> {
            bookingService.makeReservations(bookingRequestAlreadyDone);
        });
        assertEquals(BookingException.ErrorCode.OBJECT_CANNOT_BE_SAVED, bookingException.getErrorCode());

    }

}
