package org.example.chess_clone.service;

import lombok.RequiredArgsConstructor;
import org.example.chess_clone.exceptionHandling.ApplicationExceptions;
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

    public Optional<User> getUserByEmailId(String emailId) {
        Optional<User> res;
        try{
            res = userRepo.findUserByUserEmailId(emailId);
        }
        catch (Exception e){
            throw new ApplicationExceptions.UserNotFound("EmailId is not found");
        }
        return res;
    }

    public List<User> getAllUsers() {
        List<User> res = userRepo.findAll();
        if(res == null){ throw new ApplicationExceptions.UserNotFound("There are no users in the Database");}
        return res;
    }

    public Optional<User> getUserByUserName(String username) {
        Optional<User> res;
        try{
            res = userRepo.findUserByUsername(username);
        }
        catch (Exception e){
            throw new ApplicationExceptions.UserNotFound("Username is not found");
        }
        return res;
    }
}
