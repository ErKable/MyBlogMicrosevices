package it.cgmconsulting.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.cgmconsulting.auth.entity.enumerated.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user_")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //auto increment
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false, length = 15, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password; // encrypted -> $2a$10$uRjzxWBfvrC5UPDwjpQoV.JsZLl6ClFHZuk9fAYW39T.n1PE021Km

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;



}
