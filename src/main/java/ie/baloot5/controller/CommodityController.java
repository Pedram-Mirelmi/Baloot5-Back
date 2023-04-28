package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.exception.InvalidRequestParamsException;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.model.Commodity;
import ie.baloot5.model.CommodityDTO;
import ie.baloot5.model.User;
import ie.baloot5.model.RateDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import static ie.baloot5.Utils.Constants.*;

import java.util.*;

@RestController
public class CommodityController {
    final IRepository repository;
    final ISessionManager sessionManager;

    public CommodityController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/api/commodities/{commodityId}")
    public CommodityDTO getSingleCommodity(@RequestHeader(value = AUTH_TOKEN, required = false) String authToken,
                                           @PathVariable("commodityId") int commodityId) {
        if(sessionManager.isValidToken(authToken)) {
            try {
                User user = sessionManager.getUser(authToken).get();
                return new CommodityDTO(repository.getCommodityById(commodityId).get(),
                        repository.getInShoppingListCount(user.getUsername(), commodityId),
                        repository.getCommodityRateCount(commodityId),
                        repository.getProvider(repository.getCommodityById(commodityId).get().getProviderId()).get().getName());
            } catch (NoSuchElementException e) {
                throw new InvalidIdException("Invalid commodity Id");
            }
        }
        throw new InvalidValueException("Authentication token not valid");
    }

    @GetMapping("/api/commodities")
    public List<CommodityDTO> sortCommodities(@RequestHeader(value = AUTH_TOKEN, required = false) String authToken,
                                              @RequestParam(QUERY) String query,
                                              @RequestParam(SORT_BY) String sortBy,
                                              @RequestParam(SEARCH_BY) String searchBy,
                                              @RequestParam(AVAILABLE) boolean onlyAvailable) {
        if(sessionManager.isValidToken(authToken)) {
            try {
                List<Commodity> result;
                // searching
                result = doSearch(query, searchBy);
                // sorting
                result = doSort(result, sortBy);
                // adding inCart field
                User user = sessionManager.getUser(authToken).get();
                var stream = result.stream().map(
                        commodity -> new CommodityDTO(commodity,
                                repository.getInShoppingListCount(user.getUsername(), commodity.getId()),
                                repository.getCommodityRateCount(commodity.getId()),
                                repository.getProvider(commodity.getProviderId()).get().getName())
                );
                if(onlyAvailable) {
                    stream = stream.filter(commodityDTO -> commodityDTO.getInStock() > 0);
                }
                return stream.toList();
            }
            catch (NoSuchElementException e) {
                throw new InvalidIdException("Some id is wrong");
            }
        }
        throw new InvalidValueException("Authentication token not valid");
    }

    @PostMapping("/api/commoditiesRates")
    public RateDTO rateCommodity(@RequestHeader(AUTH_TOKEN) String authToken,
                                 @RequestBody Map<String, String> body) {
        if(sessionManager.isValidToken(authToken)) {
            long commodityId = Long.parseLong(body.get(COMMODITY_ID));
            float rate = Float.parseFloat(body.get(RATING));
            User user = sessionManager.getUser(authToken).get();
            float newRating = repository.addRating(user.getUsername(), commodityId, rate);
            return new RateDTO(SUCCESS, newRating, repository.getCommodityRateCount(commodityId));
        }
        throw new InvalidValueException("Authentication token not valid");
    }

    @GetMapping("/api/recommended")
    public List<CommodityDTO> getRecommended(@RequestHeader(AUTH_TOKEN) String authToken) {
        if(sessionManager.isValidToken(authToken)) {
            try {
                User user = sessionManager.getUser(authToken).get();
                return repository.getRecommendedCommodities(user.getUsername()).stream().map(
                            commodity -> new CommodityDTO(commodity,
                                    repository.getInShoppingListCount(user.getUsername(), commodity.getId()),
                                    repository.getCommodityRateCount(commodity.getId()),
                                    repository.getProvider(commodity.getProviderId()).get().getName())

                ).toList();
            }
            catch (NoSuchElementException | InvalidIdException e) {
                throw new InvalidValueException("Authentication token not valid");
            }
        }
        throw new InvalidValueException("Authentication token not valid");
    }

    private List<Commodity> doSort(@NotNull List<Commodity> result, String sortBy) {
        if(sortBy.equals(NAME)) {
            return result.stream().sorted(Comparator.comparing(Commodity::getName)).toList();
        }
        else if(sortBy.equals(PRICE)) {
            return result.stream().sorted(Comparator.comparing(Commodity::getPrice)).toList();
        }
        else {
            throw new InvalidRequestParamsException("Invalid sort-by parameter");
        }
    }

    private List<Commodity> doSearch(String query, String searchBy) {
        if(searchBy.equals(NAME)) {
            return  repository.searchCommoditiesByName(query);
        }
        else if(searchBy.equals(CATEGORY)) {
            return repository.getCommoditiesByCategory(query);
        }
        throw new InvalidRequestParamsException("Invalid search-by parameter");
    }
}
