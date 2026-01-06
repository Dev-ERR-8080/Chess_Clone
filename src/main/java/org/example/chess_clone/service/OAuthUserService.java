package org.example.chess_clone.service;

import org.example.chess_clone.model.User;
import org.example.chess_clone.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class OAuthUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public OAuthUserService(UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findOrCreateOAuthUser(String email, String name, String picture) {
        return userRepository.findUserByUserEmailId(email)
                .orElseGet(() -> {
                    User user = new User();
                    user.setUserEmailId(email);
                    user.setUsername(email);
                    user.setPassword(passwordEncoder.encode("OAUTH_USER"));
                    user.setRole("USER");
                    user.setFullName(name);
                    user.setPfpUrl(picture);
                    user.setOauthUser(true);
                    user.setRating(250);
                    return userRepository.save(user);
                });
    }
}
