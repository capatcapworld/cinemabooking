package dk.capworld.cinemautils.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "shows")
@Getter
public class Shows implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(name = "running_date", nullable = false)
    private Date runningDate;

    @Column(name = "created", nullable = false)
    private Date created;
}
