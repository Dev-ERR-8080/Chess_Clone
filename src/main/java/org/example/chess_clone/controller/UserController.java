package org.example.chess_clone.controller;

import org.example.chess_clone.model.User;
import org.example.chess_clone.service.UserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {

    private final UserDetailsService userService;
    public UserController(UserDetailsService userService){this.userService=userService ; }

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping("/world")
    public String secureHelloWorld(){
        return "Hello world secured";
    }

    @GetMapping("user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) { return new ResponseEntity<>(userService.getUserByUsername(username),
        HttpStatus.OK);}
}
