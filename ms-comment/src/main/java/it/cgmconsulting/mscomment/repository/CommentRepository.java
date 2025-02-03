package it.cgmconsulting.mscomment.repository;

import it.cgmconsulting.mscomment.entity.Comment;
import it.cgmconsulting.mscomment.payload.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value="SELECT new it.cgmconsulting.mscomment.payload.response.CommentResponse(" +
            "c.id, " +
            "c.comment, " +
            "c.createdAt, " +
            "CAST(c.createdBy as string) as author, " + // NEL RESULTSET TRATTO L'INT DELL'AUTORE DEL COMMENTO COME UNA STRINGA
            "c.parent.id) " +
            "FROM Comment c " +
            "WHERE c.postId = :postId " +
            "ORDER BY c.createdAt DESC")
    List<CommentResponse> getCommentsByPost(int postId);


    @Query(value="SELECT c FROM Comment c where c.id = :commentId")
    Comment getParent(int commentId);


}
