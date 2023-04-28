package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.exception.InvalidRequestParamsException;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.model.Comment;
import ie.baloot5.model.CommentDTO;
import ie.baloot5.model.CommodityDTO;
import ie.baloot5.model.User;
import org.springframework.web.bind.annotation.*;

import static ie.baloot5.Utils.Constants.*;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class CommentController {
    final IRepository repository;
    final ISessionManager sessionManager;


    public CommentController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/api/comments/{commentId}")
    public CommentDTO getSingleComment(@RequestHeader(AUTH_TOKEN) String authToken,
                                       @PathVariable(COMMENT_ID) long commentId) throws InvalidIdException{
        if(sessionManager.isValidToken(authToken)) {
            try {
                User user = sessionManager.getUser(authToken).get();
                return new CommentDTO(repository.getComment(commentId).get(),
                        repository.getUserVoteForComment(user.getUsername(), commentId)) ;
            }
            catch (NoSuchElementException e) {
                throw new InvalidIdException("Invalid comment Id");
            }
        }
        throw new InvalidValueException("Authentication token not valid");
    }

    @GetMapping("/api/comments")
    public List<CommentDTO> getCommodityComments(@RequestHeader(AUTH_TOKEN) String authToken, @RequestParam(COMMODITY_ID) long commodityId) {
        if(sessionManager.isValidToken(authToken)) {
            User user = sessionManager.getUser(authToken).get();
            return repository.getCommentsForCommodity(commodityId).stream().map(
                    comment -> new CommentDTO(comment, repository.getUserVoteForComment(user.getUsername(), comment.getCommentId()))
            ).toList();
        }
        throw new InvalidValueException("Authentication token not valid");
    }

    @PostMapping("/api/comments")
    public Map<String, String> addComment(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Comment comment) throws InvalidValueException{
        if(sessionManager.isValidToken(authToken)) {
            try {
                User user = sessionManager.getUser(authToken).get();
                repository.addComment(user.getUsername(), comment.getCommodityId(), comment.getText());
                return Map.of(STATUS, SUCCESS);
            } catch (NoSuchElementException e) {
                sessionManager.removeSession(authToken);
                throw new InvalidValueException("Invalid authentication token");
            }
        }
        throw new InvalidValueException("Authentication token not valid");
    }

    @PostMapping("/api/commentsVotes")
    public Map<String, Object> rateComment(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Map<String, Long> body) throws InvalidIdException, InvalidValueException {
        if(sessionManager.isValidToken(authToken)) {
            try {
                User user = sessionManager.getUser(authToken).get();
                Comment comment = repository.getComment(body.get(COMMENT_ID)).get();
                repository.addVote(user.getUsername(), comment.getCommentId(), (int)body.get(VOTE).longValue());
                return Map.of(STATUS, SUCCESS,
                        LIKES, comment.getLikes(),
                        DISLIKES, comment.getDislikes());
            }
            catch (NoSuchElementException e) {
                throw new InvalidRequestParamsException("Invalid comment Id");
            }
        }
        throw new InvalidValueException("Authentication token not valid");
    }
}
