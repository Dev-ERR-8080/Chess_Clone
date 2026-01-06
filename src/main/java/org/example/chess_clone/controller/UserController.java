package org.example.chess_clone.controller;

import org.example.chess_clone.model.DTO.UserResponseDto;
import org.example.chess_clone.model.User;
import org.example.chess_clone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.example.chess_clone.model.UserPrincipal; // ✅ your package

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){this.userService=userService ; }


    @GetMapping("/")
    public List<User> getAllUsers(@RequestHeader("Authorization") String token){ return userService.getAllUsers(); }

    @GetMapping("user/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String username) { return new ResponseEntity<>(userService.getUserByUserName(username),
        HttpStatus.OK);}

    @GetMapping("user/{emailId}")
    public ResponseEntity<Optional<User>> getUserByEmailId(@PathVariable String emailId) {
        System.out.println("email received in controller"+emailId);
        return new ResponseEntity<>(userService.getUserByEmailId(emailId),
        HttpStatus.OK);}

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> me(Authentication authentication) {
        System.out.println("Request received by user me end point");
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//        System.out.println(principal);
        return ResponseEntity.ok(
                new UserResponseDto(
                        principal.getUserId(),
                        principal.getUsername(),        // username
                        principal.getUserEmailId(),     // email
                        principal.getUserRole(),        // role
                        principal.getPfpUrl(),          // profile picture
                        principal.getDisplayName(),     // full name
                        principal.getCountry()          // country
                )
        );
    }

}
