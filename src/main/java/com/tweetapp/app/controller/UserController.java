package com.tweetapp.app.controller;

import com.tweetapp.app.dao.entity.User;
import com.tweetapp.app.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1.0/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/details/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable String userId) {

        User response = userService.getUserDetails(userId);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> response = userService.getAllUsers();

        if (CollectionUtils.isNotEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<User>> getSearchedUsers(@PathVariable String username) {

        List<User> response = userService.getSearchedUsers(username);

        if (CollectionUtils.isNotEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
