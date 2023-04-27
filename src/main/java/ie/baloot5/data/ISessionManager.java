package ie.baloot5.data;

import ie.baloot5.model.User;
import org.jetbrains.annotations.NotNull;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface ISessionManager {
    Optional<User> getUser(String authToken);
    String addSession(@NotNull String username,@NotNull String password) throws NoSuchAlgorithmException;

    boolean removeSession(String authToken);

    boolean isValidToken(String authToken);
}
