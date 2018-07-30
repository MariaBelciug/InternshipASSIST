package com.assist.internship.model;

import com.assist.internship.helpers.ResponseObject;
import lombok.Data;

import javax.persistence.*;


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


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName="id")
    private Course course;

}
