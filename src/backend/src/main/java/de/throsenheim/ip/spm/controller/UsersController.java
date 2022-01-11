package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.models.User;
import de.throsenheim.ip.spm.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> GetAll(Authentication authentication) {
        return ResponseEntity.ok(this.userRepository.findAll());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> DeleteUser(@RequestBody User user) {
        var userEntity = this.userRepository.getByUsername(user.getUsername());
        this.userRepository.delete(userEntity);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
