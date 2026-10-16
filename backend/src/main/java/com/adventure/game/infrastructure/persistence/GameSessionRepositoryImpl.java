package com.adventure.game.infrastructure.persistence;

import com.adventure.game.domain.model.GameSession;
import com.adventure.game.domain.repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GameSessionRepositoryImpl implements GameSessionRepository {

    private final JpaGameSessionRepository jpaGameSessionRepository;

    @Override
    public GameSession save(GameSession gameSession) {
        return jpaGameSessionRepository.save(gameSession);
    }

    @Override
    public Optional<GameSession> findByUsernameAndBookId(String username, UUID bookId) {
        return jpaGameSessionRepository.findByUsernameAndBookId(username, bookId);
    }

    @Override
    public void delete(GameSession gameSession) {
        jpaGameSessionRepository.delete(gameSession);
    }
}
