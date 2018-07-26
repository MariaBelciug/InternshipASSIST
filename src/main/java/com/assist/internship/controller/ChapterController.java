package com.assist.internship.controller;

import com.assist.internship.helpers.TitleHelpner;
import com.assist.internship.model.Chapter;
import com.assist.internship.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.assist.internship.service.ChapterService;

import java.util.Collection;

@RestController
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ChapterRepository chapterRepository;

    @RequestMapping(value = "/chapters", method = RequestMethod.GET)
    public Collection<Chapter> getChapters() {
        return chapterService.findAll();
    }

    @RequestMapping(value="/chapter", method = RequestMethod.GET)
    public ResponseEntity users(@RequestParam("id") final int id){
        if (chapterService.findChapterById(id) != null) {
            Chapter chapter = chapterService.findChapterById(id);

            if(chapter!=null) {
                return ResponseEntity.status(HttpStatus.OK).body(chapter);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("The provided id address doesn't belong to any existing chapter.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide a valid id.");
        }
    }


    @RequestMapping(value = "/create/chapter", method = RequestMethod.POST)
    public ResponseEntity createNewChapter(@RequestBody Chapter chapter) {

        Chapter dbChapter = chapterService.findChapterById(chapter.getId());
        String title = chapter.getTitle();

        if (chapter.getTitle() != null) {
            if (!TitleHelpner.titleIsValid(title)) {
                return ResponseEntity.status(HttpStatus.OK).body("The supplied chapter title is not valid!");
            } else {
                if (dbChapter != null) {
                    return ResponseEntity.status(HttpStatus.OK).body("The selected chapter title already belongs to an chapter. Please use a different one!");
                } else {
                    chapterRepository.save(chapter);
                    return ResponseEntity.status(HttpStatus.OK).body("Chapter [" + chapter.getTitle() + "] created successfully!");
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Please fill in all the mandatory fields to successfully create the chapter!");
        }
    }
}
