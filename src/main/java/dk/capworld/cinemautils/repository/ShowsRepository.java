package dk.capworld.cinemautils.repository;

import dk.capworld.cinemautils.domain.Shows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowsRepository extends JpaRepository<Shows, Long> {
    List<Shows> findAll();
}
