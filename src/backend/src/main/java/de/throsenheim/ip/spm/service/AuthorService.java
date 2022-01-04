package de.throsenheim.ip.spm.service;

import de.throsenheim.ip.spm.models.Author;
import de.throsenheim.ip.spm.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
