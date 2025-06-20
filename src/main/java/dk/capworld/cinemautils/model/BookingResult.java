package dk.capworld.cinemautils.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Data
public class BookingResult implements Serializable {

    @Serial
    private static final long serialVersionUID = -5640207376510335668L;

    private Long id;

    private String movieName;

    private Date runningDate;

    private List<Integer> availableSeats;
}
