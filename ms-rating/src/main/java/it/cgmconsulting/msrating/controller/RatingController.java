package it.cgmconsulting.msrating.controller;

import it.cgmconsulting.msrating.service.RatingService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/v3")
    public ResponseEntity<?> addRate(
            @RequestHeader("userId") int userId,
            @RequestParam @Min(1) int postId,
            @RequestParam @Min(1) @Max(5) byte rate){
        return new ResponseEntity<>(ratingService.addRate(userId, postId, rate), HttpStatus.CREATED);
    }

    @GetMapping("/v0/{postId}")
    public ResponseEntity<?> getAvgByPost(@PathVariable @Min(1) int postId){
        Double avg = ratingService.getAvgByPost(postId);
        double avg_ = avg == null ? 0d : avg;
        return new ResponseEntity<>(avg_, HttpStatus.OK);
    }
}
