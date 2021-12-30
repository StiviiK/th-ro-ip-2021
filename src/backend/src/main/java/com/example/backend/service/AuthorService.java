package com.example.backend.service;

import com.example.backend.models.Author;
import com.example.backend.models.Paper;
import com.example.backend.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> saveMultiple(List<Author> authorList) {
        List<Author> savedAuthors = new ArrayList<>();
        for (Author author: authorList) {
            savedAuthors.add(authorRepository.save(author));
        }
        return savedAuthors;
    }
}
