package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.exception.InvalidRequestParamsException;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static ie.baloot5.Utils.Constants.*;

@RestController
public class UserController {

    final IRepository repository;
    final ISessionManager sessionManager;

    public UserController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/users/{username}")
    public User getUser(@RequestHeader(AUTH_TOKEN) String authToken, @PathVariable(USERNAME) String username) {
        if(sessionManager.isValidToken(authToken)) {
            try {
                return repository.getUser(username).get();
            }
            catch (NoSuchElementException e) {
                throw new InvalidIdException("Invalid username");
            }
        }
        throw new InvalidValueException("Authentication token invalid");
    }

    @PostMapping("/addCredit")
    public Map<String, String> addCredit(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Map<String, Long> body) throws InvalidIdException, InvalidValueException {
        if(sessionManager.isValidToken(authToken)) {
            try {
                Long amount = Objects.requireNonNull(body.get(AMOUNT));
                repository.addCredit(sessionManager.getUser(authToken).get().getUsername(), amount);
                return Map.of(STATUS, SUCCESS);
            }
            catch (NullPointerException e) {
                throw new InvalidRequestParamsException("Invalid \"amount\" in request");
            }
        }
        throw new InvalidValueException("Authentication token invalid");
    }
}
