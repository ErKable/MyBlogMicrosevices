package it.cgmconsulting.mspost.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class PostDetailResponse extends PostResponse{

    private String content;
    private double average;

    public PostDetailResponse(int id, String title, LocalDate publicationDate, String author, String content, double average) {
        super(id, title, publicationDate, author);
        this.content = content;
        this.average = average;
    }
}
