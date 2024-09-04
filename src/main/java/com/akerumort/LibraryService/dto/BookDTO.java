package com.akerumort.LibraryService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private Long id;
    private String title;
    private String genre;
    private String publicationYear;
}
