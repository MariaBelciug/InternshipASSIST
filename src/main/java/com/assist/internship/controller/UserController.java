package com.assist.internship.controller;

import com.assist.internship.model.User;
import com.assist.internship.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

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

}
