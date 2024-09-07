package com.akerumort.LibraryService.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookIdsDTO {

    @NotEmpty(message = "Book IDs cannot be null")
    private Set<Long> bookIds;
}