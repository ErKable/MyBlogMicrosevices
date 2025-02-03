package it.cgmconsulting.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$",
                message="La password può contenere solo caratteri maiuscoli e minuscoli e numeri. La lunghezza deve essere compresa tra 6 e 10 caratteri")
                String password,
        @NotBlank @Size(min = 4, max = 15) String username
) {}
