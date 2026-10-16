package com.adventure.book.application.service;

import com.adventure.book.domain.exception.BusinessRuleViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class BookJsonImportService {

    private static final long MAX_FILE_SIZE_BYTES = 1024 * 1024; //1MB
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("application/json");

    /**
     * A book is invalid if any of the following conditions are
     * met:
     * • Book has none, or more than one beginning
     * • Book has no ending (but can have multiple)
     * • Book has invalid next section id.
     * • A non-ending section has no options
     *
     * @param file
     */
    public void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BusinessRuleViolationException("IMPORT_FILE_EMPTY", "Import file is empty");
        }

    }
}
