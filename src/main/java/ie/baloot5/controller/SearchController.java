package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidRequestParamsException;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.model.Commodity;
import org.springframework.web.bind.annotation.*;

import static ie.baloot5.Utils.Constants.*;
import java.util.List;

@RestController
public class
SearchController {
    final IRepository repository;
    final ISessionManager sessionManager;

    public SearchController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/api/search")
    public List<Commodity> search(@RequestHeader(AUTH_TOKEN) String authToken, @RequestParam(SEARCH_BY) String searchBy, @RequestParam(QUERY) String query) {
        if(sessionManager.isValidToken(authToken)) {
            if(searchBy.equals(CATEGORY)) {
                return repository.getCommodityList().stream().filter(
                        commodity -> commodity.getCategories().contains(query)
                ).toList();
            }
            if(searchBy.equals(NAME)) {
                return repository.getCommodityList().stream().filter(
                        commodity -> commodity.getName().contains(query)
                ).toList();
            }
            if(searchBy.equals(PROVIDER)) {
                return repository.getCommodityList().stream().filter(
                        commodity -> repository.getProvider(commodity.getProviderId()).get().getName().equals(query)
                ).toList();
            }
            throw new InvalidRequestParamsException("Invalid search-by parameter");
        }
        throw new InvalidValueException("Authentication token invalid");
    }


}
