package com.assist.internship.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Data
@Entity
@Table(name = "question", schema = "public")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "question", cascade=CascadeType.ALL)
    private Collection<Answer> answers = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="CHAPTER_ID", referencedColumnName="id")
    private Chapter chapter;

    public Collection<Answer> getQuestions() {
        return answers;
    }

    public void setQuestions(Collection<Answer> answers) {
        this.answers = answers;
    }
}
