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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public List<BookDTO> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepository.findAll().stream().map(bookMapper::toDTO).collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        logger.info("Fetching book with ID: {}", id);
        return bookMapper.toDTO(bookRepository.findById(id).orElse(null));
    }

    public BookDTO createBook(BookDTO bookDTO) {
        logger.info("Creating new book...");

        if (bookDTO.getAuthorIds() == null || bookDTO.getAuthorIds().isEmpty()) {
            logger.error("A book must have at least one author.");
            throw new CustomException("A book must have at least one author.");
        }

        Set<Author> authors = authorService.getAuthorsByIds(bookDTO.getAuthorIds().stream().collect(Collectors.toSet()));

        if (authors.size() != bookDTO.getAuthorIds().size()) {
            logger.error("One or more of the authors listed do not exist. " +
                    "Provided IDs: {}, Existing IDs: {}", bookDTO.getAuthorIds(),
                    authors.stream().map(Author::getId).collect(Collectors.toSet()));
            throw new CustomException("One or more of the authors listed do not exist.");
        }

        Book book = bookMapper.toEntity(bookDTO);
        book.setAuthors(authors);
        Book savedBook = bookRepository.save(book);

        for (Author author : savedBook.getAuthors()) {
            author.getBooks().add(savedBook);
            authorService.saveAuthor(author);
        }

        logger.info("Book created successfully with ID: {}", savedBook.getId());
        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        logger.info("Updating book with ID: {}", id);

        if (!bookRepository.existsById(id)) {
            logger.error("Book with ID {} does not exist.", id);
            return null;
        }

        Book book = bookMapper.toEntity(bookDTO);
        book.setId(id);

        if (bookDTO.getAuthorIds() != null && !bookDTO.getAuthorIds().isEmpty()) {
            Set<Author> authors = authorService.getAuthorsByIds(bookDTO.getAuthorIds()
                    .stream().collect(Collectors.toSet()));
            if (authors.size() != bookDTO.getAuthorIds().size()) {
                logger.error("One or more of the authors listed do not exist. " +
                        "Provided IDs: {}, Existing IDs: {}", bookDTO.getAuthorIds(),
                        authors.stream().map(Author::getId).collect(Collectors.toSet()));
                throw new CustomException("One or more of the authors listed do not exist.");
            }
            book.setAuthors(authors);
        }

        Book updatedBook = bookRepository.save(book);

        for (Author author : updatedBook.getAuthors()) {
            author.getBooks().add(updatedBook);
            authorService.saveAuthor(author);
        }

        logger.info("Book updated successfully with ID: {}", updatedBook.getId());
        return bookMapper.toDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        try {
            if (bookRepository.existsById(id)) {
                bookRepository.deleteById(id);
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("Book with ID {} does not exist.", id);
            throw new CustomException("Book with ID " + id + " does not exist.");
        }
    }

    public void deleteAllBooks() {
        logger.info("Deleting all books...");
        bookRepository.deleteAll();
        logger.info("All books deleted successfully.");
    }
}
