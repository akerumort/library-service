package com.akerumort.LibraryService.mappers;

import com.akerumort.LibraryService.dto.BookDTO;
import com.akerumort.LibraryService.entities.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDTO(Book book);
    Book toEntity(BookDTO bookDTO);
}
