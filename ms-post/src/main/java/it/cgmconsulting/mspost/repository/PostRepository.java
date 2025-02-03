package it.cgmconsulting.mspost.repository;

import it.cgmconsulting.mspost.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value="SELECT p FROM Post p " +
            "WHERE (p.publicationDate IS NOT NULL AND p.publicationDate <= :now)")
    List<Post> getPublishedPosts(LocalDate now);


    @Query(value="SELECT p FROM Post p " +
            "WHERE p.id = :postId " +
            "AND (p.publicationDate IS NOT NULL AND p.publicationDate <= :now)")
    Optional<Post> getPublishedPost(int postId, LocalDate now);

}
