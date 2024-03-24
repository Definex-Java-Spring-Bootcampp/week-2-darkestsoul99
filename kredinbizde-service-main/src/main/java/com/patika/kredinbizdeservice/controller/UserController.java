package com.patika.kredinbizdeservice.controller;

import com.patika.kredinbizdeservice.model.Application;
import com.patika.kredinbizdeservice.model.User;
import com.patika.kredinbizdeservice.service.IUserService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    // create user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        System.out.println("userService: " + userService.hashCode());
        return userService.save(user);
    }

    /*
    get all users
     */
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{email}")
    public User getByEmail(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> update(@PathVariable String email, @RequestBody User user) {

        User user1 = userService.update(email, user);

        if (user1 != null){
            return ResponseEntity.ok().body(user1);
        }

        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{email}/listAllApplications")
    public List<Application> listAllApplications(@PathVariable String email) {
        User user = userService.getByEmail(email);
        if (user != null) {
            return user.getApplicationList();
        } else {
            // Handle case where user is not found, perhaps return an empty list or throw an exception
            return Collections.emptyList();
        }
    }
    
    @PostMapping("/{email}/createApplication")
    @ResponseStatus(HttpStatus.CREATED)
    public Application createApplication(@PathVariable String email, @RequestBody Application application) {
        System.out.println("userService create Application: " + userService.hashCode());
        return userService.createApplication(userService.getByEmail(email), application);
    }

}
