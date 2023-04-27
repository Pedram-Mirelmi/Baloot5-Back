package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.model.Comment;
import ie.baloot5.model.User;
import jakarta.websocket.server.PathParam;
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

    @GetMapping("/api/comments/{commentId}")
    public Comment getSingleComment(@RequestHeader(AUTH_TOKEN) String authToken, @PathVariable(COMMENT_ID) long commentId) {
        if(sessionManager.isValidToken(authToken)) {
            return repository.getComment(commentId).get();
        }
        // TODO exception handling
        return null;
    }

    @GetMapping("/api/comments")
    public List<Comment> getCommodityComments(@RequestHeader(AUTH_TOKEN) String authToken, @RequestParam(COMMODITY_ID) long commodityId) {
        if(sessionManager.isValidToken(authToken)) {
            return repository.getCommentsForCommodity(commodityId);
        }
        // TODO exception handling
        return null;
    }

    @PostMapping("/api/comments")
    public Map<String, String> addComment(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Comment comment) {
        if(sessionManager.isValidToken(authToken)) {
            User user = sessionManager.getUser(authToken).get();
            repository.addComment(user.getUsername(), comment.getCommodityId(), comment.getText());
            return Map.of(STATUS, SUCCESS);
        }
        // TODO exception handling
        return null;
    }

    @PostMapping("/api/commentsVotes")
    public Map<String, String> rateComment(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Map<String, Long> body) throws InvalidIdException, InvalidValueException {
        if(sessionManager.isValidToken(authToken)) {
            User user = sessionManager.getUser(authToken).get();
            repository.addVote(user.getUsername(), body.get(COMMENT_ID), (int)body.get(VOTE).longValue());
            return Map.of(STATUS, SUCCESS);
        }
        // TODO exception handling
        return null;
    }
}
