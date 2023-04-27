package ie.baloot5.service;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.exception.UnauthorizedException;
import ie.baloot5.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SessionManager implements ISessionManager {

    final IRepository repository;
    final private HashMap<String, User> sessions = new HashMap<>();


    public SessionManager(IRepository repository) {
        this.repository = repository;
        System.out.println("Session manager service initiated");
    }

    @Override
    public Optional<User> getUser(String authToken) {
        return Optional.ofNullable(sessions.get(authToken));
    }

    @Override
    public String addSession(@NotNull String username, @NotNull String password) throws NoSuchAlgorithmException {
        try {
            if(password.equals(repository.getUser(username).get().getPassword())) {
                final MessageDigest digest = MessageDigest.getInstance("SHA-256");
                String authToken = new String(Hex.encodeHex(digest.digest(String.format("%s:%s", username, password).getBytes())));
                sessions.put(authToken, repository.getUser(username).get());
                return authToken;
            }
            throw new UnauthorizedException("Wrong password");
        }
        catch (NoSuchElementException e) {
            throw new UnauthorizedException("Wrong username");
        }
    }

    @Override
    public boolean removeSession(String authToken) {
        if(sessions.containsKey(authToken)) {
            sessions.remove(authToken);
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidToken(String authToken) {
        return sessions.containsKey(authToken);
    }
}
