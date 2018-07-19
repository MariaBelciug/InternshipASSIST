package com.assist.internship.service;

import com.assist.internship.model.Role;
import com.assist.internship.model.User;
import com.assist.internship.repository.RoleRepository;
import com.assist.internship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;


@Service
public class UserServiceImpl implements UserService {


    private RestTemplate restTemplate;

//    public UserServiceImpl (RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByName("ADMIN");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
    }
}
