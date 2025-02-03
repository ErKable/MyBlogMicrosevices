package it.cgmconsulting.auth.controller;

import it.cgmconsulting.auth.payload.request.SignInRequest;
import it.cgmconsulting.auth.payload.request.SignUpRequest;
import it.cgmconsulting.auth.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequest request){
        return new ResponseEntity<>(userService.signup(request), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SignInRequest request){
        return new ResponseEntity<>(userService.signin(request), HttpStatus.OK);
    }

    @GetMapping("/users-by-role")
    public ResponseEntity<?> getUsersByRole(@RequestParam @NotBlank String role){
        return new ResponseEntity<>(userService.getUsersByRole(role), HttpStatus.OK);
    }


}
