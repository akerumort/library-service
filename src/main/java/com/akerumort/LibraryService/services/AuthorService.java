package com.akerumort.LibraryService.services;

import com.akerumort.LibraryService.dto.AuthorDTO;
import com.akerumort.LibraryService.entities.Author;
import com.akerumort.LibraryService.entities.Book;
import com.akerumort.LibraryService.exceptions.CustomException;
import com.akerumort.LibraryService.mappers.AuthorMapper;
import com.akerumort.LibraryService.repos.AuthorRepository;
import com.akerumort.LibraryService.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository; // final для иммутабельности
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(authorMapper::toDTO).collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        return authorMapper.toDTO(authorRepository
                .findById(id)
                .orElse(null));
    }

    public Set<Author> getAuthorsByIds(Set<Long> ids) {
        return authorRepository.findAllById(ids).stream().collect(Collectors.toSet());
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {

        if (authorDTO.getBookIds() != null) {
            Set<Book> books = bookRepository.findAllById(authorDTO.getBookIds()).stream().collect(Collectors.toSet());
            if (books.size() != authorDTO.getBookIds().size()) {
                throw new CustomException("One or more of the books listed do not exist.");
            }
            Author author = authorMapper.toEntity(authorDTO);
            author.setBooks(books);
            return authorMapper.toDTO(authorRepository.save(author));
        }

        Author author = authorMapper.toEntity(authorDTO);
        return authorMapper.toDTO(authorRepository.save(author));
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {

        if (!authorRepository.existsById(id)) {
            throw new CustomException("Author with ID " + id + " does not exist.");
        }

        Author author = authorMapper.toEntity(authorDTO);

        if (authorDTO.getBookIds() != null) {
            Set<Book> books = bookRepository.findAllById(authorDTO.getBookIds()).stream().collect(Collectors.toSet());
            if (books.size() != authorDTO.getBookIds().size()) {
                throw new CustomException("One or more of the books listed do not exist.");
            }
            author.setBooks(books);
        }
        author.setId(id);
        return authorMapper.toDTO(authorRepository.save(author));
    }

    public void deleteAuthor(Long id) {
        try {
            if (authorRepository.existsById(id)) {
                authorRepository.deleteById(id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException("Author with ID " + id + " does not exist.");
        }
    }

    public void deleteAllAuthors() {
        authorRepository.deleteAll();
    }

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }
}
