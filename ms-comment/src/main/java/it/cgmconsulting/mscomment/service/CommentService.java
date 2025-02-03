package it.cgmconsulting.mscomment.service;

import it.cgmconsulting.mscomment.entity.Comment;
import it.cgmconsulting.mscomment.exception.ResourceNotFoundException;
import it.cgmconsulting.mscomment.payload.request.CommentRequest;
import it.cgmconsulting.mscomment.payload.response.CommentResponse;
import it.cgmconsulting.mscomment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;


    public String createComment(CommentRequest request, int userId) {
        try {
            Comment parent = request.getParent() == null ? null : commentRepository.getParent(request.getParent());
            Comment comment = new Comment(
                    request.getComment(),
                    userId,
                    parent,
                    request.getPostId()
            );
            commentRepository.save(comment);
            return "Comment added succesfully";
        } catch (Exception e){
            log.error("### "+e.getMessage());
            return null;
        }
    }

    public Comment findById(int commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
    }

    public List<CommentResponse> getComments(int postId) {
        return commentRepository.getCommentsByPost(postId);
    }
}
