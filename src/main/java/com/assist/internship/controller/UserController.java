package com.assist.internship.controller;

import com.assist.internship.helpers.EmailHelper;
import com.assist.internship.helpers.InternshipResponse;
import com.assist.internship.model.User;
import com.assist.internship.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user, @RequestHeader("reset_token") final String token)
    {
        if(token.isEmpty() || token == null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "User is already Logged!", null));
        }
        else
        {
            User oldUser = userService.findUserByEmail(user.getEmail());
            String setToken = RandomStringUtils.randomAlphabetic(6);
            if(oldUser!=null)
            {
                if(user.getPassword().equals(oldUser.getPassword()))
                {
                    oldUser.setResetToken(setToken);
                    userService.saveUser(oldUser);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, oldUser.getResetToken(), Arrays.asList(oldUser)));
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided email address doesn't belong to any existing account", null));
                }
            }
            else
            {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided email address doesn't belong to any existing account", null));
            }
        }
    }

    /*@RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestBody User user)
    {   User oldUser = userService.findUserByEmail(user.getEmail());
        if(oldUser != null)
        {
            if(user.getResetToken().equals(oldUser.getResetToken()))
            {
                userService.saveUser(oldUser);
                oldUser.setResetToken("");
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, oldUser.getResetToken(), Arrays.asList(oldUser)));
            }
            else
            {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided email address doesn't belong to any existing account", null));
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided email address doesn't belong to any existing account", null));
        }
    }*/

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestHeader("reset_token") final String token)
    {
        if(token.isEmpty() || token == null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));
        }
        else
        {
            User logedUser = userService.findUserByResetToken(token);
            if(logedUser != null)
            {
                if(logedUser.getResetToken().equals(token))
                {
                    logedUser.setResetToken(null);
                    userService.saveUser(logedUser);
                    logedUser.setPassword(null);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Successfull Logout!", Arrays.asList(logedUser)));
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "No session token available!", null));
                }

            }
            else
            {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "User not logged in!", null));
            }
        }
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public ResponseEntity users(@RequestParam("email") final String email, @RequestHeader("reset_token") final String token){
        if(token.isEmpty() || token == null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));

        }
        else
        {
            if (StringUtils.isNotEmpty(email)) {
                User user = userService.findUserByEmail(email);

                if(user!=null) {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(user)));
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided email address doesn't belong to any existing accounts.", null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InternshipResponse(false, "Please provide a valid email address.", null));
            }
        }
    }

    @RequestMapping(value="/user", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody User user, @RequestHeader("reset_token") final String token)
    {
        if(token.isEmpty() || token == null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));

        }
        else
        {
            if ((long)user.getUser_id() > 0)
            {
                User newUser = userService.findUserById(user.getUser_id());
                if(newUser != null)
                {
                    newUser.setFirstName(user.getFirstName());
                    newUser.setLastName(user.getLastName());
                    newUser.setEmail(user.getEmail());
                    userService.saveUser(newUser);
                    newUser.setPassword(null);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(newUser)));
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided id doesn't belong to any existing accounts.", null));
                }
            }
            else
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InternshipResponse(false, "Please provide a valid id.", null));
            }
        }
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity resetPassword(@RequestBody User user, @RequestHeader("reset_token") final String token)
    {
        if(token.isEmpty() || token == null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));
        }
        else
        {
            User oldUser = userService.findUserByResetToken(token);
            if(oldUser != null)
            {
                oldUser.setPassword(user.getPassword());
                userService.saveUser(oldUser);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Password Changed!", null));
            }
            else
            {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided email address doesn't belong to any existing account", null));
            }
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getUsers(@RequestHeader("reset_token") final String token)
    {
        if(token.isEmpty() || token == null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, userService.findAll().toString(), null));
        }
    }

    @RequestMapping(value = "/create/user", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestBody User user) {

        User dbUser = userService.findUserByEmail(user.getEmail());
        String email = user.getEmail();

        if (EmailHelper.emailIsNotEmpty(email))
        {
            if (!EmailHelper.emailIsValid(email))
            {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The supplied email address is not valid!", null));
            }
            else
            {
                if (dbUser != null)
                {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The selected email already belongs to an account. Please use a different one!", null));
                }
                else
                {
                    userService.saveUser(user);
                    user.setPassword(null);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "User [" + user.getEmail() + "] created successfully!", Arrays.asList(user)));
                }
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Please fill in all the mandatory fields to successfully create the user!", null));
        }
    }
}

