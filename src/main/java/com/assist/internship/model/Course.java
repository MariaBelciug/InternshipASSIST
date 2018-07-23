package com.assist.internship.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "course", schema = "public")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "small_description")
    private String small_description;

    @Column(name = "long_description")
    private String long_description;

    //divided by ';'
    @Column(name = "tags")
    private String tags;

    //divided by ';'
    @Column(name = "images")
    private String images;

    @OneToMany(mappedBy = "course", cascade=CascadeType.ALL)
    private Collection<Chapter> chapters = new ArrayList<>();

    public Collection<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Collection<Chapter> chapters) {
        this.chapters = chapters;
    }
}
