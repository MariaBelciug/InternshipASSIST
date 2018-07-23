package com.assist.internship.service;

import com.assist.internship.model.User;
import java.util.Collection;
import java.util.List;

public interface UserService {

    public User findUserByEmail(String email);
    public User findUserById(long id);
    public List<User> findAll();
    public void saveUser(User user);
}