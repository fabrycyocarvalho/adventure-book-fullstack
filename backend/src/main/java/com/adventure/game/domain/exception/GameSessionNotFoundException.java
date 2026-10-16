package com.adventure.game.domain.exception;

import com.adventure.book.domain.exception.DomainException;

public class GameSessionNotFoundException extends DomainException {

    public GameSessionNotFoundException(String username) {
        super("RESOURCE_NOT_FOUND", "Game session not found with username: " + username);
    }
}