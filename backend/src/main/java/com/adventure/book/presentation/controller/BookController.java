package com.adventure.book.presentation.controller;

import com.adventure.book.application.command.AddBookSectionCommand;
import com.adventure.book.application.command.CreateBookCommand;
import com.adventure.book.application.usecase.AddBookSectionUseCase;
import com.adventure.book.application.usecase.CreateBookUseCase;
import com.adventure.book.application.usecase.DeleteBookUseCase;
import com.adventure.book.application.usecase.StartBookImportUseCase;
import com.adventure.book.domain.exception.BookNotFoundException;
import com.adventure.book.domain.model.Difficulty;
import com.adventure.book.domain.repository.BookRepository;
import com.adventure.book.presentation.dto.*;
import com.adventure.book.presentation.mapper.BookMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final CreateBookUseCase createBookUseCase;
    private final StartBookImportUseCase startBookImportUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final AddBookSectionUseCase addBookSectionUseCase;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @GetMapping
    public List<BookResponse> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<Difficulty> difficulty,
            @RequestParam(required = false) List<String> genres
    ) {
        return bookRepository
                .findAll(search, difficulty, genres)
                .stream().map(bookMapper::toResponse)
                .toList();
    }

    @GetMapping("/genres")
    public List<String> listGenres() {
        return bookRepository
                .findAllGenres();
    }

    @GetMapping("/{bookId}")
    public BookQuestResponse findById(@PathVariable UUID bookId) {
        return bookMapper.toDetailsResponse(bookRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId.toString())));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse create(@Valid @RequestBody BookRequest request) {
        return bookMapper.toResponse(
                createBookUseCase.execute(new CreateBookCommand(
                        request.title(),
                        request.author(),
                        request.description(),
                        request.difficulty(),
                        request.genre(),
                        request.durationMinutes())
                )
        );
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void processImport(@RequestParam("file") MultipartFile file) {
        startBookImportUseCase.execute(file);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID bookId) {
        deleteBookUseCase.execute(bookId);
    }

    @GetMapping(value = "/{bookId}/sections")
    public List<SectionResponse> getSections(@PathVariable UUID bookId) {
        return bookRepository.findSectionsByBookId(bookId)
                .stream()
                .map(bookMapper::toSectionResponse)
                .toList();
    }

    @PostMapping(value = "/{bookId}/sections")
    public SectionResponse addBookSection(@PathVariable UUID bookId, @Valid @RequestBody SectionRequest request) {
        return bookMapper.toSectionResponse(
                addBookSectionUseCase.execute(new AddBookSectionCommand(bookId, request.text(), request.type()))
        );
    }
}
