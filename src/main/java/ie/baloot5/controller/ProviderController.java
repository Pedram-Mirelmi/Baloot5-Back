package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.model.Provider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

import static ie.baloot5.Utils.Constants.*;


@RestController
public class ProviderController {
    final IRepository repository;
    final ISessionManager sessionManager;

    public ProviderController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/api/providers")
    public Provider getProvider(@RequestHeader(AUTH_TOKEN) String authToken, @RequestParam(PROVIDER_ID) long providerId) {
        if(sessionManager.isValidToken(authToken)) {
            try {
                return repository.getProvider(providerId).get();
            }
            catch (NoSuchElementException e) {
                throw new InvalidIdException("Invalid provider id");
            }
        }
        throw new InvalidIdException("Invalid authentication token");
    }
}
