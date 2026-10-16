package com.adventure.game.application.usecase;

import com.adventure.game.application.command.SaveGameSessionCommand;
import com.adventure.game.domain.model.GameSession;
import com.adventure.game.domain.repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveGameProgressUseCase {

    private final GameSessionRepository gameSessionRepository;

    public GameSession execute(SaveGameSessionCommand command) {
        GameSession session = gameSessionRepository
                .findByUsernameAndBookId(command.username(), command.bookId())
                .orElse(GameSession.create(command.bookId(),
                        command.currentSectionId(),
                        command.username(),
                        command.healthPoints())
                );

        session.changeSectionId(command.currentSectionId());
        session.updateHealthPoints(command.healthPoints());

        return gameSessionRepository.save(session);
    }

}
