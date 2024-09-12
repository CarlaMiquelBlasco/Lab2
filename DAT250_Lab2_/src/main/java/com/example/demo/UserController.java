package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PollManager pollManager;

    @PostMapping
    public User createUser(@RequestBody User user) {
        pollManager.addUser(user.getUsername(), user);
        return user;
    }

    @GetMapping
    public Collection<User> listUsers() {
        return pollManager.getUsers().values();
    }
}