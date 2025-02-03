package it.cgmconsulting.mscomment.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false, length = 255)
    private String comment;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private int createdBy;

    @ManyToOne
    private Comment parent;

    private boolean censored = false;

    private int postId;

    public Comment(String comment, int createdBy, Comment parent, int postId) {
        this.comment = comment;
        this.createdBy = createdBy;
        this.parent = parent;
        this.postId = postId;
    }
}
