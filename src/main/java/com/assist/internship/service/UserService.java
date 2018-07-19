package com.assist.internship.service;

import com.assist.internship.model.User;


public interface UserService {

    public User findUserByEmail(String email);
    public void saveUser(User user);
}