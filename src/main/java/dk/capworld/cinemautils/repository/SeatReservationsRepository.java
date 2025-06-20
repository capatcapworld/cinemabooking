package dk.capworld.cinemautils.repository;

import dk.capworld.cinemautils.domain.SeatReservations;
import dk.capworld.cinemautils.domain.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatReservationsRepository extends JpaRepository<SeatReservations, Long> {
    List<SeatReservations> findAllByShow(Shows show);

    Optional<SeatReservations> findByShowAndSeat(@Param("show") Shows show, @Param("seat") Integer seatId);

    @Modifying
    void deleteById(@Param("id") Long id);


    @Modifying
//    @Query("DELETE FROM InvoiceLine p WHERE p.systemDocNumber =:systemDocNumber")
    void deleteAllByShowAndSeat(@Param("show") Shows show, @Param("seat") Integer seatId);
}
