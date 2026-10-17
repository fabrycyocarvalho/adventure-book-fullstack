package com.adventure.book.application.usecase;

import com.adventure.book.application.command.DeleteSectionCommand;
import com.adventure.book.domain.exception.SectionNotFoundException;
import com.adventure.book.domain.model.Section;
import com.adventure.book.domain.repository.SectionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSectionUseCase {

    private final SectionRepository sectionRepository;

    @Transactional
    public void execute(DeleteSectionCommand command) {
        Section section = sectionRepository
                .findById(command.sectionId(), command.bookId())
                .orElseThrow(() -> new SectionNotFoundException(command.sectionId().toString()));
        sectionRepository.delete(section, command.bookId());
    }
}
