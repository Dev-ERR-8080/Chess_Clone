package org.example.chess_clone.repository;

import org.example.chess_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUserEmailId(String userEmailId);

    boolean existsByUserEmailId(String userEmailId);

    Optional<User> findUserByUsername(String username);
}