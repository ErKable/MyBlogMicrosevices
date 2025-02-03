package it.cgmconsulting.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @NotBlank @Size(min = 4, max = 15) String username,
        @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$") String password
) {}
