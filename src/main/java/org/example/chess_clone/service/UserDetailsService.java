package org.example.chess_clone.service;

import org.example.chess_clone.model.User;
import org.example.chess_clone.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService, UserDetailsPasswordService {
    @Autowired
    UserRepository userRepo;

//    @Override
//    public userDetails  loadUserByUserName(String username) throws UsernameNotFoundException {
//        User.
//    }
    public User getUserByUsername(String username){return userRepo.findUserByUsername(username);}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);
        System.out.println("username: "+user+" Password:"+user.getPassword());
        if(user == null) throw new UsernameNotFoundException("User not found with username: "+username);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    public String saveUser(User user) { return saveUser(user); }

    @Override
    public UserDetails updatePassword(UserDetails user, @Nullable String newPassword) {
        return null;
    }
}
