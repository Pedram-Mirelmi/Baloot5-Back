package ie.baloot5.service;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.StaticData;
import ie.baloot5.exception.InvalidIdException;
import ie.baloot5.exception.InvalidValueException;
import ie.baloot5.exception.NotEnoughAmountException;
import ie.baloot5.model.*;
import com.google.common.collect.HashBasedTable;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DBService implements IRepository {

    protected HashMap<String, User> users = new HashMap<>();
    protected HashMap<Long, Provider> providers = new HashMap<>();
    protected HashMap<Long, Commodity> commodities = new HashMap<>();
    protected HashMap<Long, Comment> comments = new HashMap<>();
    protected HashMap<String, Discount> discounts = new HashMap<>();
    protected HashMap<String, HashSet<String>> discountRecords = new HashMap<>();
    protected HashBasedTable<String, Long, Long> shoppingLists = HashBasedTable.create(); // user, commodity, count
    protected HashBasedTable<String, Long, Long> purchasedLists = HashBasedTable.create(); // user, commodity, count
    protected HashBasedTable<String, Long, Float> rates = HashBasedTable.create();
    protected HashBasedTable<String, Long, Integer> votes = HashBasedTable.create(); // <voter, commentId, vote>


    public DBService() throws InvalidIdException {
        System.out.println("DB service initiated");
        getData("http://5.253.25.110:5000/api/");
    }

    @Override
    public void getData(@NotNull String apiUri) throws InvalidIdException {
        Gson gson = new Gson();
        User[] usersList = gson.fromJson(getResource(apiUri + "users"), User[].class);
        for (User user : usersList)
            addUser(user);

        Provider[] providersList = gson.fromJson(getResource(apiUri + "providers"), Provider[].class);
        for (Provider provider : providersList)
            addProvider(provider);

        Commodity[] commoditiesList = gson.fromJson(getResource(apiUri + "commodities"), Commodity[].class);
        for (Commodity commodity : commoditiesList)
            addCommodity(commodity);

        Comment[] commentsList = gson.fromJson(getResource(apiUri + "comments"), Comment[].class);
        for (int i = 0; i < commentsList.length; i++) {
            commentsList[i].setCommentId(i + 1L);
            comments.put(commentsList[i].getCommentId(), commentsList[i]);
        }

        Discount[] discounts = gson.fromJson(getResource(apiUri + "discount"), Discount[].class);
        for(Discount discount : discounts) {
            addDiscount(discount);
        }
    }

    @Override
    public void addComment(@NotNull String username, long commodityId, @NotNull String commentText) throws IllegalArgumentException {
        if (!users.containsKey(username)) {
            throw new InvalidIdException("Not Such User!");
        }
        if (!commodities.containsKey(commodityId)) {
            throw new InvalidIdException("Not Such Commodity");
        }
        long commentId = comments.size() + 1;
        comments.put(commentId, new Comment(commentId, commodityId, username, commentText, LocalDateTime.now().toString()));
    }

    @Override
    public void addDiscount(Discount discount) {
        this.discounts.put(discount.getDiscountCode(), discount);
        this.discountRecords.put(discount.getDiscountCode(), new HashSet<>());
    }

    private String getResource(@NotNull String uri) {
        if (uri.equals("http://5.253.25.110:5000/api/users"))
            return StaticData.usersString;
        if (uri.equals("http://5.253.25.110:5000/api/commodities"))
            return StaticData.commoditiesString;
        if (uri.equals("http://5.253.25.110:5000/api/providers"))
            return StaticData.providersString;
        if (uri.equals("http://5.253.25.110:5000/api/comments"))
            return StaticData.commentsString;
        if(uri.equals("http://5.253.25.110:5000/api/discount"))
            return StaticData.discounts;
        return null;
//        try {
//            URL obj = new URL(uri);
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//            con.setRequestMethod("GET");
//            int responseCode = con.getResponseCode();
//            if (responseCode != 200)
//                throw new IOException("foreign api sent a response with status code " + responseCode);
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            StringBuilder sb = new StringBuilder();
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                sb.append(inputLine);
//            }
//            return sb.toString();
//        } catch (IOException e) {
//            System.out.println("Exception occurred while getting data from foreign api:" + e.getMessage());
//            exit(1);
//        }
//        return null;
    }

    @Override
    public void addUser(@NotNull User user) throws InvalidIdException {
        if (users.containsKey(user.getUsername()))
            throw new InvalidIdException("User Already exists");
        users.put(user.getUsername(), new User(user));
    }


    @Override
    public Optional<User> getUser(@NotNull String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public void addProvider(@NotNull Provider provider) throws InvalidIdException {
        if (providers.containsKey(provider.getId()))
            throw new InvalidIdException("Provider already exists");
        providers.put(provider.getId(), new Provider(provider));
    }

    @Override
    public Optional<Provider> getProvider(long id) {
        if (!providers.containsKey(id))
            throw new InvalidIdException("No such provider");
        return Optional.ofNullable(providers.get(id));
    }

    @Override
    public void addCommodity(@NotNull Commodity newCommodity) throws InvalidIdException {
        if (!providers.containsKey(newCommodity.getProviderId())) {
            throw new InvalidIdException("There is no provider with id " + newCommodity.getProviderId());
        }

        if (!commodities.containsKey(newCommodity.getId())) {
            commodities.put(newCommodity.getId(), new Commodity(newCommodity));
        } else {
            Commodity inStockCommodity = commodities.get(newCommodity.getId());
            inStockCommodity.setInStock(inStockCommodity.getInStock() + newCommodity.getInStock());
        }
    }

    @NotNull
    @Override
    public List<Commodity> getCommodityList() {
        return commodities.values().stream().toList();
    }

    @NotNull
    @Override
    public List<Commodity> getProvidersCommoditiesList(long providerId) {
        return commodities.values().stream().filter(commodity -> commodity.getProviderId() == providerId).toList();
    }


    @Override
    public Optional<Commodity> getCommodityById(long id) {
        return Optional.ofNullable(commodities.get(id));
    }

    @NotNull
    @Override
    public List<Commodity> getCommoditiesByCategory(@NotNull String category) {
        return commodities.values().stream().filter(a -> a.getCategories().contains(category)).toList();
    }

    @NotNull
    @Override
    public List<Commodity> getCommoditiesByName(@NotNull String name) {
        return commodities.values().stream().filter(a -> a.getName().equals(name.trim())).toList();
    }

    @NotNull
    @Override
    public List<Commodity> searchCommoditiesByName(@NotNull String name) {
        return commodities.values().stream().filter(a -> a.getName().contains(name)).toList();
    }

    @Override
    public void addToBuyList(@NotNull String username, long commodityId, long count) throws NotEnoughAmountException, InvalidIdException {
        if (!this.commodities.containsKey(commodityId))
            throw new InvalidIdException("invalid commodity id");

        Commodity commodity = commodities.get(commodityId);

        if (commodity.getInStock() < count)
            throw new NotEnoughAmountException("Not enough in stock");
        if (!this.users.containsKey(username))
            throw new InvalidIdException("invalid username");
        long inListCount = shoppingLists.get(username, commodityId) == null ? 0 : Objects.requireNonNull(shoppingLists.get(username, commodityId));
        shoppingLists.put(username, commodityId, count + inListCount);
    }

    @Override
    public void removeFromBuyList(@NotNull String username, long commodityId, long count) throws NotEnoughAmountException {
        long inList = shoppingLists.get(username, commodityId) == null ? 0 : Objects.requireNonNull(shoppingLists.get(username, commodityId));

        if (inList >= count) {
            if (inList == count)
                shoppingLists.remove(username, commodityId);
            else
                shoppingLists.put(username, commodityId, inList - count);
        } else {
            throw new NotEnoughAmountException("Not enough items in list");
        }
    }


    @Override
    public float addRating(@NotNull String username, long commodityId, float rate) {
        if (!users.containsKey(username))
            throw new InvalidIdException("No Such username");
        if (!commodities.containsKey(commodityId))
            throw new InvalidIdException("No Such commodity");
        rates.put(username, commodityId, rate);
        var newRating = rates.column(commodityId).values().stream().collect(Collectors.averagingDouble((Float::floatValue))).floatValue();
        commodities.get(commodityId).setRating(newRating);
        return newRating;
    }

    @Override
    public void addVote(@NotNull String voter, long commentId, int vote) throws InvalidIdException, InvalidValueException {
        if (!users.containsKey(voter))
            throw new InvalidIdException("Invalid username");
        if (!comments.containsKey(commentId))
            throw new InvalidIdException("Invalid comment id");
        if(vote != -1 && vote != 1)
            throw new InvalidValueException("Invalid vote value(Only 1 and -1)");
        Comment comment = comments.get(commentId);
        if(votes.contains(voter, commentId)) {
            comment.removeVote(Objects.requireNonNull(votes.get(voter, commentId)));
        }
        comment.addVote(vote);
        votes.put(voter, commentId, vote);
    }

    @Override
    public long getLikes(long commentId) {
        return votes.column(commentId).values().stream().filter(s -> s == 1).count();
    }

    @Override
    public long getDislikes(long commentId) {
        return votes.column(commentId).values().stream().filter(s -> s == -1).count();
    }

    @Override
    public List<Comment> getCommentsForCommodity(long commodityId) {
        return comments.values().stream().filter(c -> c.getCommodityId() == commodityId).toList();
    }

    @Override
    public void addCredit(String username, long credit) throws InvalidIdException, InvalidValueException {
        var user = users.get(username);
        if (user == null)
            throw new InvalidIdException("Invalid username");
        if(credit <= 0)
            throw new InvalidValueException("negative credit");
        user.setCredit(user.getCredit() + credit);
    }


    @Override
    public void purchase(@NotNull String username, float discount) throws NotEnoughAmountException, InvalidIdException {
        if (!users.containsKey(username))
            throw new InvalidIdException("Not such username");
        var buyList = shoppingLists.row(username).entrySet();
        long totalPrice = (long)(calculateTotalBuyListPrice(username) * discount);
        User user = users.get(username);
        if (user.getCredit() < totalPrice)
            throw new NotEnoughAmountException("Not enough credit");
        for (var entry : buyList)
            if (commodities.get(entry.getKey()).getInStock() < entry.getValue())
                throw new NotEnoughAmountException("there is not anough " + commodities.get(entry.getKey()).getName() + " in stock");
        for (var entry : buyList) {
            commodities.get(entry.getKey()).subtractStock(entry.getValue());
            long inList = purchasedLists.contains(username, entry.getKey()) ? Objects.requireNonNull(purchasedLists.get(username, entry.getKey())) : 0;
            purchasedLists.put(username, entry.getKey(), entry.getValue() + inList);
            shoppingLists.remove(username, entry.getKey());
        }
        user.setCredit(user.getCredit() - totalPrice);
    }

    @Override
    public Optional<Float> getRating(@NotNull String username, long commodityId) {
        return Optional.ofNullable(rates.get(username, commodityId));
    }

    @Override
    public long calculateTotalBuyListPrice(String username) throws InvalidIdException {
        if (!users.containsKey(username))
            throw new InvalidIdException("Not Such User");

        return shoppingLists.row(username).entrySet().stream().mapToLong(
                entry -> entry.getValue() * commodities.get(entry.getKey()).getPrice()
        ).sum();
    }

    @Override
    public List<ShoppingItem> getShoppingList(String username) throws InvalidIdException {
        if (!users.containsKey(username))
            throw new InvalidIdException("Not Such User");
        Map<Long, Long> itemsCounts = shoppingLists.row(username);
        final List<ShoppingItem> shoppingItems = new ArrayList<>();
        itemsCounts.forEach((key, value) -> shoppingItems.add(new ShoppingItem(commodities.get(key), value)));
        return shoppingItems;
    }

    @Override
    public Optional<Long> getInShoppingListCount(String username, long commodityId) {
        return Optional.ofNullable(shoppingLists.get(username, commodityId));
    }

    @Override
    public List<ShoppingItem> getPurchasedList(String username) throws InvalidIdException {
        if (!users.containsKey(username))
            throw new InvalidIdException("Not Such User");
        Map<Long, Long> itemsCounts = purchasedLists.row(username);
        final List<ShoppingItem> purchasedItems = new ArrayList<>();
        itemsCounts.forEach((key, value) -> purchasedItems.add(new ShoppingItem(commodities.get(key), value)));
        return purchasedItems;
    }

    @Override
    public Optional<Discount> getDiscount(String discountCode) {
        return Optional.ofNullable(discounts.get(discountCode));
    }

    @Override
    public boolean hasUserUsedDiscount(String discountCode, String username) throws InvalidIdException {
        if(!discountRecords.containsKey(discountCode))
            throw new InvalidIdException("Invalid Discount code");
        return discountRecords.get(discountCode).contains(username);
    }

    @Override
    public List<Commodity> getRecommendedCommodities(String username) {
        // TODO implement recommendation
        return new ArrayList<>();
    }

    @Override
    public Optional<Comment> getComment(long commentId) {
        return Optional.ofNullable(comments.get(commentId));
    }

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

    @Override
    public long getCommodityRateCount(long commodityId) {
        return rates.column(commodityId).size();
    }

    @Override
    public Optional<Integer> getUserVoteForComment(String username, long commentId) {
        return Optional.ofNullable(votes.get(username, commentId));
    }
}