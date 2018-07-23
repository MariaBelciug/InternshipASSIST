package com.assist.internship.controller;

import com.assist.internship.helpers.EmailHelper;
import com.assist.internship.model.User;
import com.assist.internship.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user)
    {
        User newUser = userService.findUserByEmail(user.getEmail());
        if (newUser != null)
        {
            if (user.getPassword().equals(newUser.getPassword()))
            {
                newUser.setResetToken(RandomStringUtils.random(255, true, true));
            }
            userService.saveUser(newUser);
            return ResponseEntity.status(HttpStatus.OK).body(newUser);
        }
    }*/

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public ResponseEntity users(@RequestParam("email") final String email){
        if (StringUtils.isNotEmpty(email)) {
            User user = userService.findUserByEmail(email);

            if(user!=null) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("The provided email address doesn't belong to any existing accounts.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide a valid email address.");
        }
    }

    @RequestMapping(value="/user", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody User user){
        if ((long)user.getUser_id() > 0)
        {
            User newUser = userService.findUserById(user.getUser_id());
            if(newUser != null)
            {
                newUser.setFirstName(user.getFirstName());
                newUser.setLastName(user.getLastName());
                newUser.setEmail(user.getEmail());
                userService.saveUser(newUser);
                return ResponseEntity.status(HttpStatus.OK).body(newUser);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.OK).body("The provided id doesn't belong to any existing accounts.");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide a valid id.");
        }
    }

    /*@RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity resetPassword(@RequestBody User user)
    {
        User newUser = userService.findUserByEmail(user.getEmail();

    }*/

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers()
    {
        return userService.findAll();
    }

    @RequestMapping(value = "/create/user", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestBody User user) {

        User dbUser = userService.findUserByEmail(user.getEmail());
        String email = user.getEmail();

        if (EmailHelper.emailIsNotEmpty(email))
        {
            if (!EmailHelper.emailIsValid(email))
            {
                return ResponseEntity.status(HttpStatus.OK).body("The supplied email address is not valid!");
            }
            else
            {
                if (dbUser != null)
                {
                    return ResponseEntity.status(HttpStatus.OK).body("The selected email already belongs to an account. Please use a different one!");
                }
                else
                {
                    userService.saveUser(user);
                    return ResponseEntity.status(HttpStatus.OK).body("User [" + user.getEmail() + "] created successfully!");
                }
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body("Please fill in all the mandatory fields to successfully create the user!");
        }
    }
}

