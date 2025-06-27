package dk.capworld.cinemautils.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "seat_reservations")
public class SeatReservations implements Serializable {

    @Serial
    private static final long serialVersionUID = -1259288125445938808L;

    protected SeatReservations() {};

    public SeatReservations(Shows show, Integer seat) {
        this.show = show;
        this.seat = seat;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="shows_id")
    private Shows show;

    @Column(name = "seat", nullable = false)
    private Integer seat;
}
