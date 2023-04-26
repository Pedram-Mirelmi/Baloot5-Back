package ie.baloot5.controller;

import static ie.baloot5.Utils.Constants.*;

import ie.baloot5.Utils.Constants;
import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:9090")
@RestController
public class AuthController {

    final IRepository repository;
    final ISessionManager sessionManager;

    public AuthController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/logout")
    public Map<String, String> logout(@RequestHeader(Constants.AUTH_TOKEN) String authToken) {
        sessionManager.removeSession(authToken);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) throws NoSuchAlgorithmException {
        String username = body.get("username");
        String password = body.get("password");
        if(username == null || password == null) {
            return null;
        }
        String authToken = sessionManager.addSession(username);
        return Map.of(AUTH_TOKEN, authToken,
                      STATUS, SUCCESS);
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        String username = body.get(USERNAME);
        String password = body.get(PASSWORD);
        String email = body.get(EMAIL);
        String birthDate = body.get(BIRTHDATE);
        String address = body.get(ADDRESS);
        Map<String, String> response = new HashMap<>();
        try {
            repository.addUser(new User(username, password, email, birthDate, address, 0));
            String authToken = sessionManager.addSession(username);
            return Map.of(STATUS, SUCCESS,
                     AUTH_TOKEN, authToken);
        }
        catch (InvalidIdException | NoSuchAlgorithmException e) { // TODO exception handling
//            response.put("")
        }
        return response;
    }

}
