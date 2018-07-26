package com.assist.internship.service;

import com.assist.internship.model.Chapter;
import com.assist.internship.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("chapterService")
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    public Chapter save(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    /*public Optional<Chapter> findById(int idChapter) {
        return chapterRepository.findById(idChapter);
    }*/
    public Collection<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    public Chapter findChapterById(int id) {
        return chapterRepository.findById(id);
    }
}
