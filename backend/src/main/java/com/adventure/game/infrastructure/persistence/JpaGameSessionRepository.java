package com.adventure.game.infrastructure.persistence;

import com.adventure.game.domain.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaGameSessionRepository extends JpaRepository<GameSession, UUID> {
    Optional<GameSession> findByUsernameAndBookId(String username,  UUID bookId);
}
