package dk.capworld.cinemautils.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
public class BookingResult implements Serializable {

    private Long id;

    private String movieName;

    private Date runningDate;

    private List<Integer> availableSeats;
}
