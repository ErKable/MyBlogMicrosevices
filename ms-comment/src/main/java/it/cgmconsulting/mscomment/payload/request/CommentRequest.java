package it.cgmconsulting.mscomment.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequest {

    @Min(1)
    private int postId;
    private Integer parent;
    @NotBlank @Size(max = 255)
    private String comment;
}
