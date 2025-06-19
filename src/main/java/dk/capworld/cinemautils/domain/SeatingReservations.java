package dk.capworld.cinemautils.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "seating_reservations")
@Getter
public class SeatingReservations implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="shows_id")
//    @Column(name = "shows_id", nullable = false)
    private Shows show;

    @Column(name = "seating", nullable = false)
    private Integer seating;

    @Column(name = "created", nullable = false)
    private Date created;
}
