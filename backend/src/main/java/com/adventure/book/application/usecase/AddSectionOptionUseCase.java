package com.adventure.book.application.usecase;

import com.adventure.book.application.command.AddSectionOptionCommand;
import com.adventure.book.domain.exception.SectionNotFoundException;
import com.adventure.book.domain.model.Section;
import com.adventure.book.domain.repository.SectionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddSectionOptionUseCase {

    private final SectionRepository sectionRepository;

    @Transactional
    public Section execute(AddSectionOptionCommand command) {
        Integer sectionId = command.sectionId();
        Section section = sectionRepository
                .findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException(sectionId.toString()));

        section.addOption(command.description(), command.tip(), command.gotoId(), command.consequence());
        sectionRepository.save(section);
        return section;
    }
}
