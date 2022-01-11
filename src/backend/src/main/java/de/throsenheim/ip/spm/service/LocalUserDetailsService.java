package de.throsenheim.ip.spm.service;

import de.throsenheim.ip.spm.models.Paper;
import de.throsenheim.ip.spm.models.User;
import de.throsenheim.ip.spm.models.UserPrincipal;
import de.throsenheim.ip.spm.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service that handles adds logic for interacting with users and loading them.
 *
 * @author Alessandro Soro
 * @author Stefan Kürzeder
 */
@Service
public class LocalUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public LocalUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Adds the paper to the liked papers of the given user.
     * @param user The user to add the paper to.
     * @param likedPaper The paper to add to the user.
     * @author Alessandro Soro
     */
    public void addLikedPaper(User user, Paper likedPaper) {
        user.addLikedPaper(likedPaper);
        userRepository.save(user);
    }

    /**
     * Removes the paper from the liked papers of the given user.
     * @param user The user to remove the paper from.
     * @param toRemove The paper to remove from the user.
     * @author Alessandro Soro
     */
    public void removeLikedPaper(User user, Paper toRemove) {
        user.removeLikedPaper(toRemove);
        userRepository.save(user);
    }

    /**
     * Adds the paper to the papers of the given user.
     * @param user The user to add the paper to.
     * @param paper The paper to add to the user.
     * @author Alessandro Soro
     */
    public void addPaper(User user, Paper paper) {
        user.addPaper(paper);
        userRepository.save(user);
    }

    /**
     * Removes the paper from the papers of the given user.
     * @param user The user to remove the paper from.
     * @param toRemove The paper to remove from the user.
     * @author Alessandro Soro
     */
    public void removeAddedPaper(User user, Paper toRemove) {
        user.removePaper(toRemove);
        userRepository.save(user);
    }

    /**
     * Load the user by the given username
     * @param username The given username of the user
     * @return The user, if found.
     * @throws UsernameNotFoundException If the user was not found.
     * @author Stefan Kürzeder
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

    /**
     * Helper used for extracting the authenticated user from the request.
     * @param authentication
     * @return The authenticated user.
     * @author Stefan Kürzeder
     */
    public User getUserByAuth(Authentication authentication) {
        var principal = (UserDetails)authentication.getPrincipal();
        var username = principal.getUsername();

        // force loading the user from the database, including all foreign tables
        UserPrincipal userDetails = (UserPrincipal) loadUserByUsername(username);
        return userDetails.getUser();
    }
}

