package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.model.Comment;
import ie.baloot5.model.User;
import org.springframework.web.bind.annotation.*;

import static ie.baloot5.Utils.Constants.*;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    final IRepository repository;
    final ISessionManager sessionManager;


    public CommentController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @GetMapping
    public List<Comment> getCommodityComments(@RequestHeader(AUTH_TOKEN) String authToken, @RequestParam("commodityId") long commodityId) {
        if(sessionManager.isValidToken(authToken)) {
            return repository.getCommentsForCommodity(commodityId);
        }
        // TODO exception handling
        return null;
    }

    @GetMapping
    public Map<String, String> addComment(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Comment comment) {
        if(sessionManager.isValidToken(authToken)) {
            User user = sessionManager.getUser(authToken).get();
            repository.addComment(user.getUsername(), comment.getCommodityId(), comment.getText());
            return Map.of(STATUS, SUCCESS);
        }
        // TODO exception handling
        return null;
    }

    @PostMapping
    public Map<String, String> rateComment(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Map<String, Long> body) {
        if(sessionManager.isValidToken(authToken)) {
            User user = sessionManager.getUser(authToken).get();
            repository.addRating(user.getUsername(), body.get(COMMODITY_ID), body.get(COMMENT_ID));
            return Map.of(STATUS, SUCCESS);
        }
        // TODO exception handling
        return null;
    }
}
