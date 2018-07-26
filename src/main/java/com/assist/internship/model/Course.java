package com.assist.internship.model;

import com.assist.internship.helpers.ResponseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "course", schema = "public")
public class Course implements ResponseObject {

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

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName="id")
    private Category category;
}
