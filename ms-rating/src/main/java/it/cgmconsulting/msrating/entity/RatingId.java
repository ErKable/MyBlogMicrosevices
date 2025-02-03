package it.cgmconsulting.msrating.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class RatingId implements Serializable {

    private int userId;
    private int postId;
    private LocalDate ratedAt;

}
