package com.example.backend.service;

import com.example.backend.models.Keyword;
import com.example.backend.models.Paper;
import com.example.backend.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    public List<Keyword> saveMultiple(List<Keyword> authorList) {
        List<Keyword> savedKeywords = new ArrayList<>();
        for (Keyword keyword: authorList) {
            savedKeywords.add(keywordRepository.save(keyword));
        }
        return savedKeywords;
    }
}
