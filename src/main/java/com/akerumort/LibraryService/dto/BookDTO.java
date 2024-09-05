package com.akerumort.LibraryService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 1, message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Genre is required")
    @Size(min = 1, message = "Genre must not be empty")
    private String genre;

    @Positive(message = "Publication year must be positive")
    @NotNull(message = "Publication year cannot be null")
    private Integer publicationYear;

    @NotNull(message = "Author IDs cannot be null")
    private Set<Long> authorIds;
}
