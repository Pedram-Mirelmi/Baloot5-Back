package ie.baloot5.controller;

import static ie.baloot5.Utils.Constants.*;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
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
    public Map<String, String> logout(@RequestHeader(AUTHENTICATION_TOKEN) String authToken) {
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
        Map<String, String> response = new HashMap<>();
        response.put("Auth-token", authToken);
        response.put("status", "success");
        return response;
    }

}
