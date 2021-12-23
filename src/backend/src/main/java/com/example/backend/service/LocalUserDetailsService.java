package com.example.backend.service;

import com.example.backend.models.Paper;
import com.example.backend.models.User;
import com.example.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LocalUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public LocalUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addLikedPaper(User user, Paper likedPaper) {
        user.addLikedPaper(likedPaper);
        return userRepository.save((user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

    public User getUserByAuth (Authentication authentication) {
        var principal = (UserDetails)authentication.getPrincipal();
        var username = principal.getUsername();
        UserPrincipal userDetails = (UserPrincipal) loadUserByUsername(username);
        return userDetails.getUser();
    }
}

