package org.example.chess_clone.controller;

import org.example.chess_clone.model.User;
import org.example.chess_clone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){this.userService=userService ; }

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping("/world")
    public String secureHelloWorld(){
        return "Hello world secured";
    }

    @GetMapping("/")
    public List<User> getAllUsers(@RequestHeader("Authorization") String token){ return userService.getAllUsers(); }

    @GetMapping("user/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String username) { return new ResponseEntity<>(userService.getUserByUserName(username),
        HttpStatus.OK);}

    @GetMapping("user/{emailId}")
    public ResponseEntity<Optional<User>> getUserByEmailId(@PathVariable String emailId) { return new ResponseEntity<>(userService.getUserByEmailId(emailId),
        HttpStatus.OK);}
}
