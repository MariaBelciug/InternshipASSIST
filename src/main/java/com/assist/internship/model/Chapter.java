package com.assist.internship.model;

import com.assist.internship.helpers.ResponseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Data
@Entity
@Table(name = "chapter", schema = "public")
public class Chapter implements ResponseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;


    @OneToMany(mappedBy = "chapter", cascade=CascadeType.ALL)
    private Collection<Question> questions = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="COURSE_ID", referencedColumnName="id")
    private Course course;

    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }

}
