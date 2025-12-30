package org.example.chess_clone.controller;

import org.example.chess_clone.model.User;
import org.example.chess_clone.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/loginTest")
    public ResponseEntity<String> loginTest(){return new ResponseEntity<>("Test Login page working ", HttpStatus.OK );}

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {

        authService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        String token = authService.login(user);
        return ResponseEntity.ok(token);
    }
}
