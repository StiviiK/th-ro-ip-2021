package com.example.backend.util;

import com.example.backend.models.User;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DemoData {

    private final UserRepository userRepository;

    public DemoData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void InitData() {
        var userCheck = userRepository.count();
        if (userCheck == 0) {
            var user = new User("foo", "$2a$12$oHiElt236IvY9s3O3acC9eFplNwQ62HyJ6paFjeYSsN/MRkIY9r3i");
            userRepository.save(user);
        }
    }
}
