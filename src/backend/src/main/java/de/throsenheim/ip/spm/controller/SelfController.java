package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.models.PaperRequest;
import de.throsenheim.ip.spm.service.LocalUserDetailsService;
import de.throsenheim.ip.spm.service.PaperService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that takes care of adding, retrieving and deleting papers from a user.
 *
 * @author Alessandro Soro
 */
@RestController
@RequestMapping(value = "/self", produces = { "application/json;charset=UTF-8" })
public class SelfController {
    private final PaperService paperService;
    private final LocalUserDetailsService userDetailsService;

    public SelfController(PaperService paperService, LocalUserDetailsService userDetailsService) {
        this.paperService = paperService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Adds a paper to the liked list of a user
     * @param authentication User authentication to append the paper to the liked list of a user
     * @param paper Paper that needs to be added to the list
     * @return added Paper.
     */
    @PutMapping(value = "/likedPaper")
    public Object addLikedPaper(Authentication authentication, @RequestBody PaperRequest paper) {
        var paperToLike = paperService.getPaper(paper.getId());
        var user = userDetailsService.getUserByAuth(authentication);
        userDetailsService.addLikedPaper(user, paperToLike);
        return ResponseEntity.ok(paperToLike);
    }

    /**
     * Removes a liked paper from the liked list of the user.
     * @param authentication User authentication to remove the paper from the liked list of a user
     * @param paper Paper that needs to be removed from the list
     * @return removed Paper.
     */
    @PostMapping(value = "/likedPaper")
    public Object removeLikedPaper(Authentication authentication, @RequestBody PaperRequest paper) {
        var paperToRemoveLike = paperService.getPaper(paper.getId());
        var user = userDetailsService.getUserByAuth(authentication);
        userDetailsService.removeLikedPaper(user, paperToRemoveLike);
        return ResponseEntity.ok(paperToRemoveLike);
    }

    /**
     * Getter of liked paper list from a user.
     * @param authentication User authentication to get the liked list of the user.
     * @return list of Papers.
     */
    @GetMapping(value = "/likedPapers")
    public Object getLikedPapers(Authentication authentication) {
        var user = userDetailsService.getUserByAuth(authentication);
        var likedPapers = user.getLikedPapers();
        return ResponseEntity.ok(likedPapers);
    }

    /**
     * Getter of list of papers added by the user.
     * @param authentication User authentication to get the added papers list of the user.
     * @return list of Papers.
     */
    @GetMapping(value = "/addedPapers")
    public Object getAddedPapers(Authentication authentication) {
        var user = userDetailsService.getUserByAuth(authentication);
        var addedPapers = user.getPapers();
        return ResponseEntity.ok(addedPapers);
    }

    /**
     * Removes paper from the list of added papers by the user.
     * @param authentication User authentication to get the added papers list of the user.
     * @param paper Paper that needs to be removed from the list
     * @return removed Paper.
     */
    @PostMapping(value = "/addedPapers")
    public Object removeAddedPapers(Authentication authentication, @RequestBody PaperRequest paper) {
        var paperToRemove = paperService.getPaper(paper.getId());
        var user = userDetailsService.getUserByAuth(authentication);
        userDetailsService.removeAddedPaper(user, paperToRemove);
        return ResponseEntity.ok(paperToRemove);
    }
}
