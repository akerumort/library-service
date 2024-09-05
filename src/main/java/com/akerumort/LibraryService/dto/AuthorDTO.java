package com.akerumort.LibraryService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AuthorDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 1, message = "First name must not be empty")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, message = "Last name must not be empty")
    private String lastName;

    @NotBlank(message = "Country is required")
    @Size(min = 1, message = "Country must not be empty")
    private String country;

    private Set<Long> bookIds = new HashSet<>();;
}
