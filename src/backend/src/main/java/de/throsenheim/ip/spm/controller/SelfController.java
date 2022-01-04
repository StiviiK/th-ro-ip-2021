package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.models.Paper;
import de.throsenheim.ip.spm.service.LocalUserDetailsService;
import de.throsenheim.ip.spm.service.PaperService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/self", produces = { "application/json;charset=UTF-8" })
public class SelfController {
    private final PaperService paperService;
    private final LocalUserDetailsService userDetailsService;

    public SelfController(PaperService paperService, LocalUserDetailsService userDetailsService) {
        this.paperService = paperService;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/addLikedPaper", method = RequestMethod.PUT)
    public Object addLikedPaper(Authentication authentication, @RequestBody Paper paper) {
        var paperToLike = paperService.getPaper(paper.getId());
        var user = userDetailsService.getUserByAuth(authentication);
        userDetailsService.addLikedPaper(user, paperToLike);
        ;
        return ResponseEntity.ok(paperToLike);
    }

    @RequestMapping(value = "/removeLikedPaper", method = RequestMethod.PUT)
    public Object removeLikedPaper(Authentication authentication, @RequestBody Paper paper) {
        var paperToRemoveLike = paperService.getPaper(paper.getId());
        var user = userDetailsService.getUserByAuth(authentication);
        userDetailsService.removeLikedPaper(user, paperToRemoveLike);
        ;
        return ResponseEntity.ok(paperToRemoveLike);
    }

    @RequestMapping(value = "/getLikedPapers", method = RequestMethod.GET)
    public Object getLikedPapers(Authentication authentication) {
        var user = userDetailsService.getUserByAuth(authentication);
        var likedPapers = user.getLikedPapers();
        return ResponseEntity.ok(likedPapers);
    }

    @RequestMapping(value = "/addedPapers", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
    public ResponseEntity<List<Paper>> getAddedPapers(Authentication authentication) {
        var user = userDetailsService.getUserByAuth(authentication);
        var addedPapers = user.getPapers();
        return ResponseEntity.ok(addedPapers);
    }
}
