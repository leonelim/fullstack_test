package com.pistophone.factory.controller;

import com.pistophone.factory.config.UserDetailsImpl;
import com.pistophone.factory.model.User;
import com.pistophone.factory.repository.UserRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);
    private UserRepository userRepository;
    Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/current-user")
    public User getUser(@AuthenticationPrincipal UserDetailsImpl user) {
        return userRepository.findById(user.getId()).get();
    }
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findUsers();
    }
}
