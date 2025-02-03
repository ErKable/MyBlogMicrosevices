package it.cgmconsulting.msrating.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.EqualsAndHashCode.Include;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rating {

    @EmbeddedId
    @Include
    private RatingId ratingId;

    private byte rate;
}
