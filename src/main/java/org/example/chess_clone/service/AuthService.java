package org.example.chess_clone.service;

import org.example.chess_clone.exceptionHandling.ApplicationExceptions;
import org.example.chess_clone.model.DTO.LoginDto;
import org.example.chess_clone.model.DTO.RegisterDto;
import org.example.chess_clone.model.User;
import org.example.chess_clone.model.UserPrincipal;
import org.example.chess_clone.repository.UserRepository;
import org.example.chess_clone.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // ================= REGISTER =================
    public void register(RegisterDto dto) {

        if (userRepository.existsByUserEmailId(dto.getUserEmailId())) {
            throw new ApplicationExceptions.EmailAlreadyExists("Email already exists. Try sign in");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setUserEmailId(dto.getUserEmailId());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getUserFullName());
        // server-controlled fields
        user.setRole("ROLE_USER");
        user.setOauthUser(false);
        user.setPfpUrl(null);
        user.setRating(250);

        userRepository.save(user);
    }


    // ================= LOGIN =================
    public String login(LoginDto loginDto) {
//        System.out.println("DTO = " + loginDto);
//        System.out.println(loginDto.getUserEmailId()+" "+loginDto.getPassword());

        if (loginDto.getUserEmailId() == null || loginDto.getPassword() == null) {
            throw new ApplicationExceptions.BadRequest("Email and password are required");
        }
        Authentication authentication ;
        try{
            authentication =
                    authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getUserEmailId(),
                                loginDto.getPassword()
                        )
                );
            }catch (Exception e) {
                throw new ApplicationExceptions.UserNotFound("Invalid Email or password");
            }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return jwtUtil.generateToken(userPrincipal);
    }

}
