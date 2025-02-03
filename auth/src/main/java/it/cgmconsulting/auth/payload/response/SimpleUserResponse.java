package it.cgmconsulting.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SimpleUserResponse {

    private int id;
    private String username;
}
