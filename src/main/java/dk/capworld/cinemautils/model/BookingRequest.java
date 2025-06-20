package dk.capworld.cinemautils.model;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@Validated
public class BookingRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -3506617856205443360L;

    private Long showId;
    private List<@Max(value = 50, message = "Seat number must be at max 50") Integer> seats;
}
