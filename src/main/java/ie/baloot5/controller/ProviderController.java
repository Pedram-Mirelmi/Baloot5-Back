package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.model.Provider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/providers")
    public Provider getProvider(@RequestHeader(AUTH_TOKEN) String authToken, @RequestParam(PROVIDER_ID) long providerId) {
        if(sessionManager.isValidToken(authToken)) {
            Optional<Provider> provider = repository.getProvider(providerId);
            if(provider.isPresent()) {
                return provider.get();
            }
        }
        return null;
    }
}
