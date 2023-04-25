package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.model.Commodity;
import ie.baloot5.model.User;
import jakarta.websocket.server.PathParam;
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

    @GetMapping("/commodities")
    public List<Commodity> getCommodities(@RequestHeader(AUTH_TOKEN) String authToken) {
        if(sessionManager.isValidToken(authToken)) {
            return repository.getCommodityList();
        }
        return new ArrayList<>();
    }

    @GetMapping("/commodities/{commodityId}")
    public Commodity getSingleCommodity(@RequestHeader(AUTH_TOKEN) String authToken, @PathParam("commodityId") int commodityId) {
        if(sessionManager.isValidToken(authToken)) {
            var commodity = repository.getCommodityById(commodityId);
            // TODO exception handling
            return commodity.orElse(null);
        }
        // TODO
        return null;
    }

    @GetMapping("/commodities")
    public List<Commodity> sortCommodities(@RequestHeader(AUTH_TOKEN) String authToken, @RequestParam(SORT_BY) String sortBy) {
        if(sessionManager.isValidToken(authToken)) {
            if(sortBy.equals(ID)) {
                return repository.getCommodityList().stream().sorted(Comparator.comparingLong(Commodity::getId)).toList();
            }
            if(sortBy.equals(PRICE)) {
                return repository.getCommodityList().stream().sorted(Comparator.comparingLong(Commodity::getPrice)).toList();
            }
            if(sortBy.equals(RATING)) {
                return repository.getCommodityList().stream().sorted(Comparator.comparingDouble(Commodity::getRating)).toList();
            }
            // TODO exception handling
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    @PostMapping("/rateCommodity")
    public Map<String, String> rateCommodity(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Map<String, String> body) {
        if(sessionManager.isValidToken(authToken)) {
            long commodityId = Long.parseLong(body.get(COMMODITY_ID));
            float rate = Float.parseFloat(body.get(RATING));
            User user = sessionManager.getUser(authToken).get();
            repository.addRating(user.getUsername(), commodityId, rate);
            return Map.of(STATUS, SUCCESS);
        }
        return new HashMap<>();
    }

    @GetMapping("/recommended")
    public List<Commodity> getRecommended(@RequestHeader(AUTH_TOKEN) String authToken) {
        if(sessionManager.isValidToken(authToken)) {
            User user = sessionManager.getUser(authToken).get();
            return repository.getRecommendedCommodities(user.getUsername());
        }
        // TODO exception handling
        return null;
    }
}
