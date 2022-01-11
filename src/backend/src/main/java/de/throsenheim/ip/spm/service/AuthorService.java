package de.throsenheim.ip.spm.service;

import de.throsenheim.ip.spm.models.Author;
import de.throsenheim.ip.spm.repository.AuthorRepository;
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
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

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
