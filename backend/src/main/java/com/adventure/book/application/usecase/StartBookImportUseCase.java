package com.adventure.book.application.usecase;

import com.adventure.book.domain.exception.BookAlreadyExistsException;
import com.adventure.book.domain.exception.BusinessRuleViolationException;
import com.adventure.book.domain.model.Book;
import com.adventure.book.domain.model.Option;
import com.adventure.book.domain.model.Section;
import com.adventure.book.domain.model.SectionType;
import com.adventure.book.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StartBookImportUseCase {

    private final BookRepository bookRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void execute(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BusinessRuleViolationException("IMPORT_FILE_EMPTY", "Import file is empty");
        }

        byte[] rawContent = readBytes(file);
        String content = new String(rawContent, StandardCharsets.UTF_8);
        Book book = objectMapper.readValue(content, Book.class);
        validate(book);
        bookRepository.save(book);
    }

    private void validate(Book book) {
        //Book has none, or more than one beginning
        long countBegging = book.sections().stream().map(Section::type).filter(SectionType.BEGIN::equals).count();
        if (countBegging == 0 || countBegging > 1) {
            throw new BusinessRuleViolationException("IMPORT_FILE_INVALID", "Book has none, or more than one beginning");
        }

        //Book has no ending (but can have multiple)
        long countEnding = book.sections().stream().map(Section::type).filter(SectionType.END::equals).count();
        if (countEnding == 0) {
            throw new BusinessRuleViolationException("IMPORT_FILE_INVALID", "Book has no ending");
        }

        //Book has invalid next section id.
        List<Integer> sectionsIds = book.sections().stream().map(Section::id).toList();

        long invalidNextSectionId = book.sections()
                .stream()
                .filter(section -> section.options() != null)
                .flatMap(section -> section.options().stream())
                .map(Option::gotoId)
                .distinct()
                .filter(gotoId -> !sectionsIds.contains(gotoId))
                .count();
        if (invalidNextSectionId > 0) {
            throw new BusinessRuleViolationException("IMPORT_FILE_INVALID", "Book has " + invalidNextSectionId + " invalid next section id.");
        }

        //A non-ending section has no options
        boolean sectionHasNoOptions = book.sections()
                .stream()
                .filter(section -> SectionType.NODE.equals(section.type()))
                .anyMatch(p -> p.options().isEmpty());
        if (sectionHasNoOptions) {
            throw new BusinessRuleViolationException("IMPORT_FILE_INVALID", "A non-ending section has no options");
        }

        bookRepository.findByTitleAndAuthorIgnoreCase(book.title(), book.author()).ifPresent(exits -> {
            throw new BookAlreadyExistsException(exits.title(), exits.author());
        });
    }

    private static byte[] readBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new BusinessRuleViolationException("IMPORT_FILE_UNREADABLE", "Could not read import file");
        }
    }
}
