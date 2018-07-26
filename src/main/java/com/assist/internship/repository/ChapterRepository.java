package com.assist.internship.repository;

import com.assist.internship.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long>{

    Chapter findById(int idChapter);
}
