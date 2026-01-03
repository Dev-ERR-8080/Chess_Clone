package org.example.chess_clone.service;

import lombok.RequiredArgsConstructor;
import org.example.chess_clone.model.User;
import org.example.chess_clone.model.UserPrincipal;
import org.example.chess_clone.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUserEmailId(username).orElseThrow(()->new UsernameNotFoundException("user not found: "+username));
        return new UserPrincipal(user);
    }

}
