package com.adventure.book.presentation.controller;


import com.adventure.book.application.command.AddSectionOptionCommand;
import com.adventure.book.application.usecase.AddSectionOptionUseCase;
import com.adventure.book.application.usecase.DeleteSectionUseCase;
import com.adventure.book.domain.model.Consequence;
import com.adventure.book.presentation.dto.ConsequenceRequest;
import com.adventure.book.presentation.dto.OptionRequest;
import com.adventure.book.presentation.dto.SectionResponse;
import com.adventure.book.presentation.mapper.BookMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
public class SectionController {

    private final AddSectionOptionUseCase addSectionOptionUseCase;
    private final DeleteSectionUseCase deleteSectionUseCase;
    private final BookMapper bookMapper;

    @PostMapping(value = "/{sectionId}/options")
    public SectionResponse addSectionOption(@PathVariable Integer sectionId, @Valid @RequestBody OptionRequest request) {
        return bookMapper.toSectionResponse(
                addSectionOptionUseCase.execute(
                        new AddSectionOptionCommand(
                                sectionId,
                                request.description(),
                                request.gotoId(),
                                request.tip(),
                                parseConsequence(request.consequence()))
                )
        );
    }

    @DeleteMapping("/{sectionId}")
    public void remove(@PathVariable Integer sectionId) {
        deleteSectionUseCase.execute(sectionId);
    }

    private static @Nullable Consequence parseConsequence(ConsequenceRequest req) {
        return Objects.nonNull(req) ? new Consequence(req.value(), req.text(), req.type()) : null;
    }
}
