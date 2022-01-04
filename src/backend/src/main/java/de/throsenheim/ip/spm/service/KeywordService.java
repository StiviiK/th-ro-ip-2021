package de.throsenheim.ip.spm.service;

import de.throsenheim.ip.spm.models.Keyword;
import de.throsenheim.ip.spm.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
