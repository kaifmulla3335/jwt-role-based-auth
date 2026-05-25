package com.example.authdemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity                          // Tells JPA: "This class = a database table"
@Table(name = "users")           // Table will be named "users"
@Data                            // Lombok: generates getters, setters, toString
@Builder                         // Lombok: allows User.builder().name("Kaif").build()
@NoArgsConstructor               // Lombok: generates empty constructor
@AllArgsConstructor              // Lombok: generates constructor with all fields
public class User {

    @Id                                              // This is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment: 1, 2, 3...
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)         // Email must be unique
    private String email;

    @Column(nullable = false)
    private String password;                         // Will store BCrypt hash, NOT plain text

    @Enumerated(EnumType.STRING)                     // Store "USER" or "ADMIN" as text in DB
    @Column(nullable = false)
    private Role role;
}