package org.example.chess_clone.repository;

import org.example.chess_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String userName);

    boolean existsByUsername(String userName);
}