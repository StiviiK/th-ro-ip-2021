package de.throsenheim.ip.spm.util;

import de.throsenheim.ip.spm.models.User;
import de.throsenheim.ip.spm.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Populates the database with demo data if empty
 *
 * @author Stefan Kürzeder
 */
@Component
public class DemoData {

    private final UserRepository userRepository;

    public DemoData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initData() {
        var userCheck = userRepository.count();
        if (userCheck == 0) {
            // Username: foo, Password: foo
            var user = new User("foo", "$2a$12$oHiElt236IvY9s3O3acC9eFplNwQ62HyJ6paFjeYSsN/MRkIY9r3i");
            userRepository.save(user);
        }
    }
}
