package com.adventure.game.application.usecase;

import com.adventure.game.application.command.DeleteGameSessionCommand;
import com.adventure.game.domain.repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteGameProgressUseCase {

    private final GameSessionRepository gameSessionRepository;

    public void execute(DeleteGameSessionCommand command) {
        gameSessionRepository.findByUsernameAndBookId(command.username(), command.bookId())
                .ifPresent(gameSessionRepository::delete);
    }

}
