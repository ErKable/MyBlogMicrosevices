package it.cgmconsulting.msrating.repository;

import it.cgmconsulting.msrating.entity.Rating;
import it.cgmconsulting.msrating.entity.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {


    // CREATE OR REPLACE VIEW  calc_avg AS SELECT r.post_id, COALESCE(ROUND(AVG(r.rate), 0.0),2) AS average FROM rating r GROUP BY r.post_id;
    @Query(value="SELECT average FROM calc_avg WHERE post_id = :postId", nativeQuery = true)
    Double calcAverage(int postId);
}
