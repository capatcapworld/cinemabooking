package dk.capworld.cinemautils.model;

import jakarta.validation.constraints.Max;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record BookingResult(Long id, String movieName, LocalDateTime runningDate, List<Integer> availableSeats) implements Serializable {

    @Serial
    private static final long serialVersionUID = -5640207376510335668L;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String movieName;
        private LocalDateTime runningDate;
        private List<@Max(value = 50, message = "Seat number must be at max 50") Integer> availableSeats;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder movieName(String movieName) {
            this.movieName = movieName;
            return this;
        }

        public Builder runningDate(LocalDateTime runningDate) {
            this.runningDate = runningDate;
            return this;
        }

        public Builder availableSeats(List<@Max(value = 50, message = "Seat number must be at max 50") Integer> availableSeats) {
            this.availableSeats = availableSeats;
            return this;
        }

        public BookingResult build() {
            return new BookingResult(id, movieName, runningDate, availableSeats);
        }
    }
}
