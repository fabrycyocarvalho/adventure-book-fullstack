package com.adventure.game.presentation.mapper;

import com.adventure.game.domain.model.GameSession;
import com.adventure.game.presentation.dto.GameSessionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameSessionMapper {

    GameSessionResponse toResponse(GameSession gameSession);
}
