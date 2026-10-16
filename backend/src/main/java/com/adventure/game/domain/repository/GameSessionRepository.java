package com.adventure.game.domain.repository;

import com.adventure.game.domain.model.GameSession;

import java.util.Optional;
import java.util.UUID;

public interface GameSessionRepository {
    GameSession save(GameSession gameSession);
    Optional<GameSession> findByUsernameAndBookId(String username, UUID bookId);
    void delete(GameSession gameSession);
}
