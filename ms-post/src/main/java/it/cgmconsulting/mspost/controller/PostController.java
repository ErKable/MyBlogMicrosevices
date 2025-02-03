package it.cgmconsulting.mspost.controller;

import it.cgmconsulting.mspost.payload.request.PostRequest;
import it.cgmconsulting.mspost.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/v2") // http://localhost:9090/ms-post/v2
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequest request, @RequestHeader("userId") int userId){
      try{
          return new ResponseEntity<>(postService.createPost(request, userId), HttpStatus.CREATED);
      } catch(Exception e){
          return new ResponseEntity<>("Something went wrong writing this post", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/v2/{postId}") // http://localhost:9090/ms-post/v2
    public ResponseEntity<?> updatePost(
            @RequestBody @Valid PostRequest request,
            @RequestHeader("userId") int userId,
            @PathVariable @Min(1) int postId){
        try{
            return new ResponseEntity<>(postService.updatePost(request, userId, postId), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>("Something went wrong updating this post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/v2/{postId}") // http://localhost:9090/ms-post/v2
    @CacheEvict(value = "tutti-i-post", allEntries = true)
    public ResponseEntity<?> publishPost(
            @RequestParam @FutureOrPresent LocalDate publicationDate,
            @RequestHeader("userId") int userId,
            @PathVariable @Min(1) int postId){
        try{
            return new ResponseEntity<>(postService.publishPost(publicationDate, userId, postId), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>("Something went wrong publishing this post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/v0/posts")
    @Cacheable("tutti-i-post")
    public ResponseEntity<?> getPublishedPosts(){
        log.info("## Sono nel metodo getPublishedPosts");
        return new ResponseEntity<>(postService.getPublishedPosts(), HttpStatus.OK);
    }

    @GetMapping("/v0/posts/{postId}")
    public ResponseEntity<?> getPostDetail(@PathVariable @Min(1) int postId){
        return new ResponseEntity<>(postService.getPostDetail(postId), HttpStatus.OK);
    }


}
