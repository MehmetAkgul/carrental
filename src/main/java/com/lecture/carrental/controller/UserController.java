package com.lecture.carrental.controller;


import com.lecture.carrental.domain.User;
import com.lecture.carrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@RequestMapping()
@AllArgsConstructor
@RestController
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    public UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Boolean>> registerUser(@RequestBody User user) {
        userService.register(user);

        Map<String, Boolean> map = new HashMap<>();

        map.put("User register successfuly!", true);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
