package org.example.chess_clone.service;

import lombok.RequiredArgsConstructor;
import org.example.chess_clone.model.User;
import org.example.chess_clone.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;

    public Optional<User> getUserByEmailId(String emailId) { return userRepo.findUserByUserEmailId(emailId); }
    public List<User> getAllUsers() { return userRepo.findAll(); }

    public Optional<User> getUserByUserName(String username) { return userRepo.findUserByUsername(username);    }
}
