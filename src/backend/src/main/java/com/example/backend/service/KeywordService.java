package com.example.backend.service;

import com.example.backend.models.Keyword;
import com.example.backend.models.Paper;
import com.example.backend.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Takes care of the CURD operations for the keywords.
 *
 * @author Lukas Metzner
 */
@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    /**
     * Save a list of keywords that is returned by the KeywordService.
     * @param keywordList Keywords to store.
     * @return Stored keywords.
     */
    public List<Keyword> saveMultiple(List<Keyword> keywordList) {
        List<Keyword> savedKeywords = new ArrayList<>();
        for (Keyword keyword: keywordList) {
            savedKeywords.add(keywordRepository.save(keyword));
        }
        return savedKeywords;
    }
}
