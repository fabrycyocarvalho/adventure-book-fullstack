package com.adventure.book.presentation.dto;

import java.util.List;
import java.util.UUID;

public record BookQuestResponse(UUID id,
                                String title,
                                List<SectionResponse> sections) {
}
