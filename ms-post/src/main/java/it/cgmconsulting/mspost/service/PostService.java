package it.cgmconsulting.mspost.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import it.cgmconsulting.mspost.configuration.Constants;
import it.cgmconsulting.mspost.entity.Post;
import it.cgmconsulting.mspost.exception.ResourceNotFoundException;
import it.cgmconsulting.mspost.payload.request.PostRequest;
import it.cgmconsulting.mspost.payload.response.PostDetailResponse;
import it.cgmconsulting.mspost.payload.response.PostResponse;
import it.cgmconsulting.mspost.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final Map<Integer, String> getWriters;
    private final PostCircuitService postCircuitService;

    public String createPost(PostRequest request, int userId){
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdBy(userId)
                .build();
        postRepository.save(post);
        return "Post having title "+request.getTitle()+" has been created";
    }

    @Transactional
    public String updatePost(PostRequest request, int userId, int postId){
        Post post = findById(postId);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUpdatedBy(userId);
        post.setPublicationDate(null);
        return "Post having title "+request.getTitle()+" has been updated";
    }

    @Transactional
    public String publishPost(LocalDate publicationDate, int userId, int postId){
        Post post = findById(postId);
        post.setPublicationDate(publicationDate);
        post.setUpdatedBy(userId);
        return "Post having title "+post.getTitle()+" has been published";
    }

    public Post findById(int postId){
        return postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
    }

    public Post getPublishedPost(int postId){
        return postRepository.getPublishedPost(postId, LocalDate.now())
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
    }


    public List<PostResponse> getPublishedPosts() {

        // creazione List<Post> pubblicati
        // ciclo List<Post> creando contestualmente una List<PostResponse> in cui sostituisco
        // l'id createdBy con la relativa stringa username (estratta dal bean getWriters)
        List<Post> posts = postRepository.getPublishedPosts(LocalDate.now());
        List<PostResponse> list = new ArrayList<>();

        for(Post p : posts){
            list.add(new PostResponse(p.getId(), p.getTitle(), p.getPublicationDate(), getWriters.get(String.valueOf(p.getCreatedBy()))));
        }
        return list;
    }


    public PostDetailResponse getPostDetail(int postId) {

        Post p = getPublishedPost(postId);
        PostDetailResponse pr = new PostDetailResponse(
                p.getId(),
                p.getTitle(),
                p.getPublicationDate(),
                getWriters.get(String.valueOf(p.getCreatedBy())),
                p.getContent(),
                postCircuitService.getAvgByPost(postId)
        );
        return pr;
    }

}
