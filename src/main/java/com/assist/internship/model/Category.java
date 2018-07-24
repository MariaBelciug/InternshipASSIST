package com.assist.internship.model;

import com.assist.internship.helpers.ResponseObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category", schema = "public")
public class Category implements ResponseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="category_id")
    int category_id;

    @Column(name="category_name")
    String category_name;

}
