package com.adventure.game.application.command;

import java.util.UUID;

public record SaveGameSessionCommand(UUID bookId, Integer currentSectionId, String username, Integer healthPoints) {
}
