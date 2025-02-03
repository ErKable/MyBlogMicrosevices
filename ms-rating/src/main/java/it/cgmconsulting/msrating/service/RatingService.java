package it.cgmconsulting.msrating.service;

import it.cgmconsulting.msrating.entity.Rating;
import it.cgmconsulting.msrating.entity.RatingId;
import it.cgmconsulting.msrating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;


    public String addRate(int userId, int postId, byte rate) {
        // L'utente può votare un determinato post al massimo una volta al giorno.
        // Ciò implica che si deve verificare che l'utente non abbia già votato il post in questione
        // prima di persistere un ulteriore Rating

        RatingId rId = new RatingId(userId, postId, LocalDate.now());

        if(ratingRepository.existsById(rId))
            return "Today you already rate this post; please retry tomorrow";

        ratingRepository.save(new Rating(rId, rate));
        return "Your rate has been registered";
    }


    public Double getAvgByPost(int postId) {
        return ratingRepository.calcAverage(postId);
    }
}
