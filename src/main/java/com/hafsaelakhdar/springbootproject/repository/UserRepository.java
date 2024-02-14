package com.hafsaelakhdar.springbootproject.repository;

import java.util.Optional;

import com.hafsaelakhdar.springbootproject.entities.Role;
import com.hafsaelakhdar.springbootproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    User findByRole(Role role);
}