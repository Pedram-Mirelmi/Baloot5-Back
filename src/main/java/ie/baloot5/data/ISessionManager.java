package ie.baloot5.data;

import ie.baloot5.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface ISessionManager {
    Optional<User> getUser(String authToken);
    String addSession(String username) throws NoSuchAlgorithmException;

    boolean removeSession(String authToken);
}
