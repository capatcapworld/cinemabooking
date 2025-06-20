package dk.capworld.cinemautils.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class BookingRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -3506617856205443360L;

    private Long showId;
    private List<Integer> seats;
}
