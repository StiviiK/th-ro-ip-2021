package com.example.backend.controller;

import com.example.backend.models.Paper;
import com.example.backend.service.LocalUserDetailsService;
import com.example.backend.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private PaperService paperService;

    @Autowired
    private LocalUserDetailsService userDetailsService;

    @RequestMapping(value = "/likedPaper", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public Object addLikedPaper(Authentication authentication, @RequestBody Paper paper) {
        var paperToLike = paperService.getPaper(paper.getId());
        var user = userDetailsService.getUserByAuth(authentication);
        userDetailsService.addLikedPaper(user, paperToLike);;
        return ResponseEntity.ok(paperToLike);
    }

    @RequestMapping(value = "/likedPaper", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Object removeLikedPaper(Authentication authentication, @RequestBody Paper paper) {
        var paperToRemoveLike = paperService.getPaper(paper.getId());
        var user = userDetailsService.getUserByAuth(authentication);
        userDetailsService.removeLikedPaper(user, paperToRemoveLike);;
        return ResponseEntity.ok(paperToRemoveLike);
    }

    @RequestMapping(value = "/likedPapers", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public Object getLikedPapers(Authentication authentication) {
        var user = userDetailsService.getUserByAuth(authentication);
        var likedPapers = user.getLikedPapers();
        return ResponseEntity.ok(likedPapers);
    }

    @RequestMapping(value = "/addedPapers", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<Paper>> getAddedPapers(Authentication authentication) {
        var user = userDetailsService.getUserByAuth(authentication);
        var addedPapers = user.getPapers();
        return ResponseEntity.ok(addedPapers);
    }
}
