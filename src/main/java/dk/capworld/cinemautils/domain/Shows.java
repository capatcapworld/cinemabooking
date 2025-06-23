package dk.capworld.cinemautils.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "shows")
public class Shows implements Serializable {

    @Serial
    private static final long serialVersionUID = 6044351571359036355L;

    public Shows() {};
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
