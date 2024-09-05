package com.akerumort.LibraryService.controllers;

import com.akerumort.LibraryService.dto.AuthorDTO;
import com.akerumort.LibraryService.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO author = authorService.getAuthorById(id);
        return author != null ? ResponseEntity.ok(author) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id,
                                                  @RequestBody @Valid AuthorDTO authorDTO) {
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDTO);
        return updatedAuthor != null ? ResponseEntity.ok(updatedAuthor) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllAuthors() {
        authorService.deleteAllAuthors();
        return ResponseEntity.noContent().build();
    }
}
