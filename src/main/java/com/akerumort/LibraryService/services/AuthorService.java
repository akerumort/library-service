package com.akerumort.LibraryService.services;

import com.akerumort.LibraryService.dto.AuthorDTO;
import com.akerumort.LibraryService.entities.Author;
import com.akerumort.LibraryService.mappers.AuthorMapper;
import com.akerumort.LibraryService.repos.AuthorRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository; // final для иммутабельности
    private final AuthorMapper authorMapper;

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(authorMapper::toDTO).collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        return authorMapper.toDTO(authorRepository.findById(id).orElse(null));
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        return authorMapper.toDTO(authorRepository.save(author));
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        if (!authorRepository.existsById(id)) {
            return null;
        }
        Author author = authorMapper.toEntity(authorDTO);
        author.setId(id);
        return authorMapper.toDTO(authorRepository.save(author));
    }

    public void deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        }
    }

    public void deleteAllAuthors() {
        authorRepository.deleteAll();
    }
}
