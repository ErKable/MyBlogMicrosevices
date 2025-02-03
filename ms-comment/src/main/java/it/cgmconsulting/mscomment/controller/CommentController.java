package it.cgmconsulting.mscomment.controller;

import it.cgmconsulting.mscomment.payload.request.CommentRequest;
import it.cgmconsulting.mscomment.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/v3")
    public ResponseEntity<?> createComment(
            @RequestBody @Valid CommentRequest request,
            @RequestHeader("userId") int userId){
        String msg = commentService.createComment(request, userId);
        if(msg != null)
            return new ResponseEntity<>(msg, HttpStatus.OK);
        return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/v0/{postId}")
    public ResponseEntity<?> getComments(@PathVariable @Min(1) int postId){
        return new ResponseEntity<>(commentService.getComments(postId), HttpStatus.OK);
    }
}
