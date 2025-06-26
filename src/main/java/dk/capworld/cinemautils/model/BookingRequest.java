package dk.capworld.cinemautils.model;

import jakarta.validation.constraints.Max;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Validated
public record BookingRequest(Long showId, List<@Max(value = 50, message = "Seat number must be at max 50") Integer> seats) implements Serializable {
    @Serial
    private static final long serialVersionUID = -3506617856205443360L;

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Long showId() {
        return showId;
    }

    @Override
    public List<Integer> seats() {
        return seats;
    }

    public static class Builder {
        private Long showId;
        private List<@Max(value = 50, message = "Seat number must be at max 50") Integer> seats;

        public Builder showId(Long showId) {
            this.showId = showId;
            return this;
        }

        public Builder seats(List<@Max(value = 50, message = "Seat number must be at max 50") Integer> seats) {
            this.seats = seats;
            return this;
        }

        public BookingRequest build() {
            return new BookingRequest(showId, seats);
        }
    }
}
