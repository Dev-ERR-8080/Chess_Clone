package org.example.chess_clone.repository;

import org.example.chess_clone.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveRepository extends JpaRepository<Move,Integer> {
}
