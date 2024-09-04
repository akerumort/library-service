package com.akerumort.LibraryService.mappers;

import com.akerumort.LibraryService.dto.AuthorDTO;
import com.akerumort.LibraryService.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDTO(Author author);
    Author toEntity(AuthorDTO authorDTO);
}
