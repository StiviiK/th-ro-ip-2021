package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.models.User;
import de.throsenheim.ip.spm.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller that handles all requests for information about the Users.
 *
 * @author Stefan Kürzeder
 */
@RestController
@RequestMapping(value = "/users", produces = {"application/json;charset=UTF-8"})
public class UsersController {
    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<User>> getAll(Authentication authentication) {
        return ResponseEntity.ok(this.userRepository.findAll());
    }
}
