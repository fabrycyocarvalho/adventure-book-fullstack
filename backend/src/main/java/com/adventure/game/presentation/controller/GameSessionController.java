package com.adventure.game.presentation.controller;


import com.adventure.game.application.command.DeleteGameSessionCommand;
import com.adventure.game.application.command.SaveGameSessionCommand;
import com.adventure.game.application.usecase.DeleteGameProgressUseCase;
import com.adventure.game.application.usecase.SaveGameProgressUseCase;
import com.adventure.game.domain.model.GameSession;
import com.adventure.game.domain.repository.GameSessionRepository;
import com.adventure.game.presentation.dto.GameSessionRequest;
import com.adventure.game.presentation.dto.GameSessionResponse;
import com.adventure.game.presentation.mapper.GameSessionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game-sessions")
@RequiredArgsConstructor
public class GameSessionController {
    private final SaveGameProgressUseCase saveGameProgressUseCase;
    private final DeleteGameProgressUseCase deleteGameProgressUseCase;
    private final GameSessionRepository gameSessionRepository;
    private final GameSessionMapper gameSessionMapper;

    @GetMapping("/{username}/{bookId}")
    public GameSessionResponse findByUsername(@PathVariable String username, @PathVariable UUID bookId) {
        GameSession session = gameSessionRepository.findByUsernameAndBookId(username, bookId).orElse(null);

        if (session == null) {
            return null;
        }

        return gameSessionMapper.toResponse(session);
    }

    @PostMapping
    public GameSessionResponse saveProgress(@Valid @RequestBody GameSessionRequest request) {
        return gameSessionMapper.toResponse(
                saveGameProgressUseCase.execute(
                        new SaveGameSessionCommand(request.bookId(), request.currentSectionId(), request.username(), request.healthPoints())
                )
        );
    }

    @DeleteMapping("/{username}/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProgress(@PathVariable String username, @PathVariable UUID bookId) {
        deleteGameProgressUseCase.execute(new DeleteGameSessionCommand(username, bookId));
    }

}
