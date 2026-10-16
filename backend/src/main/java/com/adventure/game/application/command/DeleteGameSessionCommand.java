package com.adventure.game.application.command;

import java.util.UUID;

public record DeleteGameSessionCommand(String username, UUID bookId) {
}
