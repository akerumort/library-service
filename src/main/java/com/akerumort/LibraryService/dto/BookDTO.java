package com.akerumort.LibraryService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookDTO {

    private Long id;
    private String title;
    private String genre;
    private Integer publicationYear;
    private Set<Long> authorIds;
}
