package com.adventure.game.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@Entity
@Table(name = "game_sessions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "book_id")
    private UUID bookId;

    @Column(name = "current_section_id")
    private Integer currentSectionId;

    private String username;

    @Column(name = "health_points")
    private Integer healthPoints;

    private GameSession(UUID bookId, Integer currentSectionId, String username, Integer healthPoints) {
        this.bookId = bookId;
        this.currentSectionId = currentSectionId;
        this.username = username;
        this.healthPoints = healthPoints;
    }

    public static GameSession create(UUID bookId, Integer currentSectionId, String username, Integer healthPoints) {
        return new GameSession(bookId, currentSectionId, username, healthPoints);
    }

    public void changeSectionId(Integer newSectionId) {
        this.currentSectionId = newSectionId;
    }

    public void updateHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }
}
