package com.payswift.repository;

import com.payswift.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users>findByEmail(String email);
    Optional<Users>findByConfirmationToken(String token);

    List<Users>findByFirstName(String firstName);

}
