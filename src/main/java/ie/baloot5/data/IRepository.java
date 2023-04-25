package ie.baloot5.data;

import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.exception.NotEnoughAmountException;
import ie.baloot5.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface IRepository {
    void getData(@NotNull String apiUri);

    void addComment(@NotNull String username, long commodityId, @NotNull String commentText) throws IllegalArgumentException;

    void addUser(@NotNull User user);

    Optional<User> getUser(@NotNull String username);

    void addProvider(@NotNull Provider provider);

    Optional<Provider> getProvider(long id);

    void addCommodity(@NotNull Commodity commodity);

    void addDiscount(Discount discount);

    List<Commodity> getCommodityList();

    List<Commodity> getProvidersCommoditiesList(long providerId);

    Optional<Commodity> getCommodityById(long id);

    List<Commodity> getCommoditiesByCategory(@NotNull String category);

    List<Commodity> getCommoditiesByName(@NotNull String name);

    List<Commodity> searchCommoditiesByName(@NotNull String name);

    void addToBuyList(@NotNull String username, long commodityId, long count) throws IllegalArgumentException, NotEnoughAmountException, InvalidIdException;

    void removeFromBuyList(@NotNull String username, long commodityId, int count) throws IllegalArgumentException, NotEnoughAmountException;

    void clearRepository();

    float addRating(@NotNull String username, long commodityId, float rate);

    void addVote(@NotNull String voter, long commentId, int vote) throws InvalidIdException, InvalidValueException;

    long getLikes(long commentId);

    long getDislikes(long commentId);

    List<Comment> getCommentsForCommodity(long commodityId);

    void addCredit(String username, long credit) throws InvalidIdException, InvalidValueException;

    void purchase(@NotNull String username, double discount) throws NotEnoughAmountException, InvalidIdException;

    Optional<Float> getRating(@NotNull String username, long commodityId);

    long calculateTotalBuyListPrice(String username) throws InvalidIdException;

    Map<Long, Long> getBuyList(String username) throws InvalidIdException;

    Map<Long, Long> getPurchasedList(String username) throws InvalidIdException;

    Optional<Discount> getDiscount(String discountCode);

    boolean hasUserUsedDiscount(String discountCode, String username) throws InvalidIdException;
}
