package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.exception.InvalidRequestParamsException;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.exception.NotEnoughAmountException;
import ie.baloot5.model.Discount;
import ie.baloot5.model.ShoppingItem;
import ie.baloot5.model.User;
import org.springframework.web.bind.annotation.*;

import static ie.baloot5.Utils.Constants.*;

import java.util.*;


@RestController
public class ShoppingController {
    final IRepository repository;
    final ISessionManager sessionManager;

    public ShoppingController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }



    @PostMapping("/api/shoppingList/add")
    public Map<String, Object> addToShoppingList(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Map<String, Long> body) throws InvalidIdException, NotEnoughAmountException {
        if(sessionManager.isValidToken(authToken)) {
            try {
                User user = sessionManager.getUser(authToken).get();
                long commodityId = Objects.requireNonNull(body.get(COMMODITY_ID));
                long count = Objects.requireNonNull(body.get(COUNT));
                repository.addToBuyList(user.getUsername(), commodityId, count);
                return Map.of(STATUS, SUCCESS,
                              "shoppingList", repository.getShoppingList(user.getUsername()));
            }
            catch (NullPointerException e) {
                throw new InvalidRequestParamsException("Invalid commodity id or count");
            }
            catch (NoSuchElementException e) {
                throw new InvalidValueException("Authentication token invalid");
            }
        }
        throw new InvalidValueException("Authentication token invalid");
    }

    @GetMapping("/api/shoppingList")
    public List<ShoppingItem> getShoppingList(@RequestHeader(AUTH_TOKEN) String authToken) throws InvalidIdException {
        if(sessionManager.isValidToken(authToken)) {
            try {
                return repository.getShoppingList(sessionManager.getUser(authToken).get().getUsername());
            }
            catch (NoSuchElementException e) {
                throw new InvalidValueException("Authentication token invalid");
            }
        }
        throw new InvalidValueException("Authentication token invalid");
    }

    @PostMapping("/api/shoppingList/remove")
    public Map<String, String> removeFromShoppingList(@RequestHeader(AUTH_TOKEN) String authToken, @RequestBody Map<String, Long> body) throws NotEnoughAmountException {
        if(sessionManager.isValidToken(authToken)) {
            try {
                User user = sessionManager.getUser(authToken).get();
                long commodityId = Objects.requireNonNull(body.get(COMMODITY_ID));
                long count = Objects.requireNonNull(body.get(COUNT));
                repository.removeFromBuyList(user.getUsername(), commodityId, count);
                return Map.of(STATUS, SUCCESS);
            }
            catch (NullPointerException e) {
                throw new InvalidRequestParamsException("Invalid commodityId or count");
            }
            catch (NoSuchElementException e) {
                throw new InvalidValueException("Authentication token invalid");
            }
        }
        throw new InvalidValueException("Authentication token invalid");
    }

    @GetMapping("/api/purchasedList")
    public List<ShoppingItem> getPurchasedList(@RequestHeader(AUTH_TOKEN) String authToken) throws InvalidIdException {
        if(sessionManager.isValidToken(authToken)) {
            try {
                return repository.getPurchasedList(sessionManager.getUser(authToken).get().getUsername());
            }
            catch (NoSuchElementException e) {
                throw new InvalidValueException("Authentication token invalid");
            }
        }
        throw new InvalidValueException("Authentication token invalid");
    }

    @GetMapping("/api/pay")
    public Map<String, String> purchase(@RequestHeader(AUTH_TOKEN) String authToken, @RequestParam("discountCode") Optional<String> discountCode) throws InvalidIdException, NotEnoughAmountException {
        if(sessionManager.isValidToken(authToken)) {
            try {
                User user = sessionManager.getUser(authToken).get();
                float discount = 1.0F;
                if(discountCode.isPresent() && repository.getDiscount(discountCode.get()).isPresent()) {
                    discount = repository.getDiscount(discountCode.get()).get().getDiscount() / 100F;
                }
                repository.purchase(user.getUsername(), discount);
                return Map.of(STATUS, SUCCESS);
            }
            catch (NoSuchElementException e) {
                throw new InvalidValueException("Authentication token invalid");
            }
        }
        throw new InvalidValueException("Authentication token invalid");
    }

    @GetMapping("/api/discount")
    public Map<String, String> validateDiscountCode(@RequestHeader(AUTH_TOKEN) String authToken,
                                                    @RequestParam("discountCode") String discountCode) {
        if(sessionManager.isValidToken(authToken)) {
                Optional<Discount> discount = repository.getDiscount(discountCode);
                return discount.isPresent() ?
                        Map.of(CODE_STATUS, VALID, DISCOUNT, String.valueOf(discount.get().getDiscount())) :
                        Map.of(CODE_STATUS, INVALID);
        }
        throw new InvalidValueException("Authentication token invalid");
    }

}
