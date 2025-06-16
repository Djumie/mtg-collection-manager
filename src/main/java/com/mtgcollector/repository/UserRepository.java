package com.mtgcollector.repository;

import com.mtgcollector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA automatically generates this query based on method name
    Optional<User> findByUsername(String username);
    // This prevents duplicate email addresses during registration
    Optional<User> findByEmail(String email);
    // Check if username already exists (useful for validation)
    boolean existsByUsername(String username);
    // Check if email already exists (useful for validation)
    boolean existsByEmail(String email);

    //Custom query using JPQL (Java Persistence Query Language)
    @Query("SELECT u FROM User u WHERE u.createdAt >= :date")
    List<User> findUsersCreatedAfter(@Param("date")LocalDateTime date);
}