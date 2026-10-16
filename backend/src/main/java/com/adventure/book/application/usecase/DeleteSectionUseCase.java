package com.adventure.book.application.usecase;

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
    public void execute(Integer sectionId) {
        Section book = sectionRepository
                .findById(sectionId).orElseThrow(() -> new SectionNotFoundException(sectionId.toString()));
        sectionRepository.delete(book);
    }
}
