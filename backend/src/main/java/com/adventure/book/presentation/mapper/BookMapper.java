package com.adventure.book.presentation.mapper;


import com.adventure.book.domain.model.Book;
import com.adventure.book.domain.model.Consequence;
import com.adventure.book.domain.model.Option;
import com.adventure.book.domain.model.Section;
import com.adventure.book.presentation.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "chaptersCount", expression = "java(book.chaptersCount())")
    BookResponse toResponse(Book book);

    BookQuestResponse toDetailsResponse(Book book);

    SectionResponse toSectionResponse(Section section);

    OptionResponse toOptionResponse(Option option);

    ConsequenceResponse toConsequenceResponse(Consequence consequence);
}
