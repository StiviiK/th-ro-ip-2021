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
@RestController
public class UserController {
    @Autowired
    private PaperService paperService;

    @Autowired
    private LocalUserDetailsService userDetailsService;

    @RequestMapping(value = "/addLikedPaper", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public Object addLikedPaper(Authentication authentication, @RequestBody Paper paper) {
        var paperToLike = paperService.getPaper(paper.getId());
        var user = userDetailsService.getUserByAuth(authentication);
        userDetailsService.addLikedPaper(user, paperToLike);;
        return ResponseEntity.ok(paperToLike);
    }

    @RequestMapping(value = "/removeLikedPaper", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public Object removeLikedPaper(Authentication authentication, @RequestBody Paper paper) {
        var paperToRemoveLike = paperService.getPaper(paper.getId());
        var user = userDetailsService.getUserByAuth(authentication);
        userDetailsService.removeLikedPaper(user, paperToRemoveLike);;
        return ResponseEntity.ok(paperToRemoveLike);
    }

    @RequestMapping(value = "/getLikedPapers", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public Object getLikedPapers(Authentication authentication) {
        var user = userDetailsService.getUserByAuth(authentication);
        var likedPapers = user.getLikedPapers();
        return ResponseEntity.ok(likedPapers);
    }
}
