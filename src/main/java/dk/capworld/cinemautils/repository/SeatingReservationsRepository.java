package dk.capworld.cinemautils.repository;

import dk.capworld.cinemautils.domain.SeatingReservations;
import dk.capworld.cinemautils.domain.Shows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatingReservationsRepository extends JpaRepository<SeatingReservations, Long> {
    List<SeatingReservations> findAllByShow(Shows show);
}
