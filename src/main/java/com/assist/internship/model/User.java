package com.assist.internship.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    int user_id;

    @Column(name = "email")
    String email;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    String password;

    @Column(name = "active")
    int active;

    @Column(name = "reset_token")
    String resetToken;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
