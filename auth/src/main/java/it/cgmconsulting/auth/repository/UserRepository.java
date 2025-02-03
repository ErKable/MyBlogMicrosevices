package it.cgmconsulting.auth.repository;
import it.cgmconsulting.auth.entity.User;
import it.cgmconsulting.auth.entity.enumerated.Role;
import it.cgmconsulting.auth.payload.response.SimpleUserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);

    @Query(value="SELECT new it.cgmconsulting.auth.payload.response.SimpleUserResponse(" +
            "u.id, " +
            "u.username) " +
            "FROM User u " +
            "WHERE u.role = :role ")
    Set<SimpleUserResponse> getUsersByRole(Role role);

}