package com.akerumort.LibraryService.services;

import com.akerumort.LibraryService.dto.BookDTO;
import com.akerumort.LibraryService.entities.Author;
import com.akerumort.LibraryService.entities.Book;
import com.akerumort.LibraryService.exceptions.CustomException;
import com.akerumort.LibraryService.mappers.BookMapper;
import com.akerumort.LibraryService.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDTO).collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        return bookMapper.toDTO(bookRepository.findById(id).orElse(null));
    }

    public List<Book> getBooksByIds(Set<Long> ids) {
        return bookRepository.findAllById(ids);
    }

    public BookDTO createBook(BookDTO bookDTO) {

        if (bookDTO.getAuthorIds() == null || bookDTO.getAuthorIds().isEmpty()) {
            throw new CustomException("A book must have at least one author.");
        }

        Set<Author> authors = authorService.getAuthorsByIds(bookDTO.getAuthorIds().stream().collect(Collectors.toSet()));

        if (authors.size() != bookDTO.getAuthorIds().size()) {
            throw new CustomException("One or more of the authors listed do not exist.");
        }

        Book book = bookMapper.toEntity(bookDTO);
        book.setAuthors(authors);
        Book savedBook = bookRepository.save(book);

        for (Author author : savedBook.getAuthors()) {
            author.getBooks().add(savedBook);
            authorService.saveAuthor(author);
        }

        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        if (!bookRepository.existsById(id)) {
            return null;
        }

        Book book = bookMapper.toEntity(bookDTO);
        book.setId(id);

        if (bookDTO.getAuthorIds() != null && !bookDTO.getAuthorIds().isEmpty()) {
            Set<Author> authors = authorService.getAuthorsByIds(bookDTO.getAuthorIds().stream().collect(Collectors.toSet()));
            if (authors.size() != bookDTO.getAuthorIds().size()) {
                throw new CustomException("One or more of the authors listed do not exist.");
            }
            book.setAuthors(authors);
        }

        Book updatedBook = bookRepository.save(book);

        for (Author author : updatedBook.getAuthors()) {
            author.getBooks().add(updatedBook);
            authorService.saveAuthor(author);
        }

        return bookMapper.toDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        try {
            if (bookRepository.existsById(id)) {
                bookRepository.deleteById(id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException("Book with ID " + id + " does not exist.");
        }
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }
}
