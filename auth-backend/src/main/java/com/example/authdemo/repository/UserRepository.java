package com.example.authdemo.repository;

import com.example.authdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository<User, Long> gives us: save(), findById(), findAll(), delete(), etc. for FREE
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA auto-generates the SQL for this just from the method name!
    // SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);
}