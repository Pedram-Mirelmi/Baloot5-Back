package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static ie.baloot5.Utils.Constants.*;

@RestController
public class UserController {

    final IRepository repository;
    final ISessionManager sessionManager;

    public UserController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/api/users/{username}")
    public User getUser(@RequestHeader(AUTH_TOKEN) String authToken, @PathVariable(USERNAME) String username) {
        if(sessionManager.isValidToken(authToken)) {
            return repository.getUser(username).get();
        }
        return null;
    }

    @PostMapping("/api/addCredit")
    public Map<String, String> addCredit(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Map<String, Long> body) throws InvalidIdException, InvalidValueException {
        if(sessionManager.isValidToken(authToken)) {
            Long amount = body.get(AMOUNT);
            repository.addCredit(sessionManager.getUser(authToken).get().getUsername(), amount);
            return Map.of(STATUS, SUCCESS);
        }
        return null;
    }
}
