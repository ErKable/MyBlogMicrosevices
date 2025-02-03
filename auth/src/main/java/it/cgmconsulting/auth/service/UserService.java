package it.cgmconsulting.auth.service;

import it.cgmconsulting.auth.entity.User;
import it.cgmconsulting.auth.entity.enumerated.Role;
import it.cgmconsulting.auth.exception.ConflictException;
import it.cgmconsulting.auth.exception.ResourceNotFoundException;
import it.cgmconsulting.auth.payload.request.SignInRequest;
import it.cgmconsulting.auth.payload.request.SignUpRequest;
import it.cgmconsulting.auth.payload.response.SimpleUserResponse;
import it.cgmconsulting.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String signup(SignUpRequest request){
        if (userRepository.existsByEmailOrUsername(request.email(), request.username())){
            throw new ConflictException("Username or email already in use");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.MEMBER)
                .enabled(true)
                .build();

        userRepository.save(user);

        return "User successfully registered";
    }

    public String signin(SignInRequest request){
        // verificare se esiste utente e password
        User user = findByUsername(request.username());

        if(!checkPassword(request.password(), user.getPassword()))
            throw new ConflictException("Wrong password");

        // verificare se è abilitato
        if(!user.isEnabled())
            throw new ConflictException("User disabled");

        // generare il jwt da restituire nella response
        return jwtService.generateToken(user);
    }

    protected User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User", "username", username));
    }

    protected User findById(int id){
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
    }

    protected boolean checkPassword(String rawPwd, String dbPwd){
        return passwordEncoder.matches(rawPwd, dbPwd);
    }


    public Map<Integer, String> getUsersByRole(String role) {
        Set<SimpleUserResponse> users = userRepository.getUsersByRole(Role.valueOf(role.toUpperCase()));
        Map<Integer, String> usersMap = users.stream()
                .collect(Collectors.toMap(SimpleUserResponse::getId, SimpleUserResponse::getUsername));
        return usersMap;
    }
}
