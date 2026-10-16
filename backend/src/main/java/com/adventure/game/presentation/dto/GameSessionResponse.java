package com.adventure.game.presentation.dto;

import java.util.UUID;

public record GameSessionResponse(UUID bookId,
                                  Integer currentSectionId,
                                  String username,
                                  Integer healthPoints) {
}
