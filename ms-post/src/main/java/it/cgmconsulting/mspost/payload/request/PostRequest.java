package it.cgmconsulting.mspost.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequest {

    @NotBlank @Size(max = 100, min = 2)
    private String title;
    @NotBlank @Size(max = 10000, min = 10)
    private String content;
}
