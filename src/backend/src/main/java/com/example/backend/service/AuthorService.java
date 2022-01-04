package com.example.backend.service;

import com.example.backend.models.Author;
import com.example.backend.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Takes care of the CURD operations for the authors.
 *
 * @author Lukas Metzner
 */
@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Save a list of authors that is returned by the arxiv.org api.
     * @param authorList Authors to store.
     * @return Stored authors.
     */
    public List<Author> saveMultiple(List<Author> authorList) {
        List<Author> savedAuthors = new ArrayList<>();
        for (Author author: authorList) {
            savedAuthors.add(authorRepository.save(author));
        }
        return savedAuthors;
    }
}
