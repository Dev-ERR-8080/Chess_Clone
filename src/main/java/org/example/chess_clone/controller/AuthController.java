package org.example.chess_clone.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.chess_clone.model.DTO.LoginDto;
import org.example.chess_clone.model.DTO.RegisterDto;
import org.example.chess_clone.model.User;
import org.example.chess_clone.service.AuthService;
import org.example.chess_clone.utils.JwtCookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth/") // for development only
public class AuthController {

    private final AuthService authService;
    private final JwtCookieUtil jwtCookieUtil;

    public AuthController(AuthService authService, JwtCookieUtil jwtCookieUtil) {
        this.authService = authService;
        this.jwtCookieUtil = jwtCookieUtil;
    }



    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterDto registerDto
    ) {
        authService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginDto loginDto,
            HttpServletResponse response
    ) {
        System.out.println("request og login reached the auth controller");
        String token = authService.login(loginDto);

        response.addHeader(
                "Set-Cookie",
                jwtCookieUtil.createJwtCookieHeader(token, false) // false = dev
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        response.addHeader(
                "Set-Cookie",
                jwtCookieUtil.clearJwtCookieHeader(false)
        );
        return ResponseEntity.ok().build();
    }


}
