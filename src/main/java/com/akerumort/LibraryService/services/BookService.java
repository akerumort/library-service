package com.akerumort.LibraryService.services;

import com.akerumort.LibraryService.dto.BookDTO;
import com.akerumort.LibraryService.entities.Author;
import com.akerumort.LibraryService.entities.Book;
import com.akerumort.LibraryService.mappers.BookMapper;
import com.akerumort.LibraryService.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        Book book = bookMapper.toEntity(bookDTO);

        if (bookDTO.getAuthorIds() != null && !bookDTO.getAuthorIds().isEmpty()) {
            Set<Author> authors = authorService.getAuthorsByIds(bookDTO.getAuthorIds().stream().collect(Collectors.toSet()));
            book.setAuthors(authors);
        }

        Book savedBook = bookRepository.save(book);

        // Обновление авторов с новой книгой
        if (savedBook.getAuthors() != null) {
            for (Author author : savedBook.getAuthors()) {
                author.getBooks().add(savedBook);
                authorService.saveAuthor(author); // Добавляем метод saveAuthor в AuthorService
            }
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
            book.setAuthors(authors);
        }

        return bookMapper.toDTO(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }
}
