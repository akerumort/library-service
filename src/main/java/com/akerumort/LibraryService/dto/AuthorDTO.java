package com.akerumort.LibraryService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AuthorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String country;
    private Set<Long> bookIds;
}
