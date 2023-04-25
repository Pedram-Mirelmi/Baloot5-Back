package ie.baloot5.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Commodity {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;
    @SerializedName("providerId")
    private long providerId;

    @SerializedName("price")
    private long price;
    @SerializedName("categories")
    private List<String> categories;
    @SerializedName("rating")
    private float rating;
    @SerializedName("inStock")
    private long inStock;

    public Commodity(long id, String name, long providerId, long price, List<String> categories, float rating, long inStock) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
    }

    public Commodity(Commodity commodity) {
        this(commodity.id, commodity.name, commodity.providerId, commodity.price, commodity.categories.stream().toList(), commodity.rating, commodity.getInStock());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getInStock() {
        return inStock;
    }

    public void setInStock(long inStock) {
        this.inStock = inStock;
    }

    public void addStuck(long newAmount) {
        this.inStock += newAmount;
    }

    public void subtractStock(long amount) {
        if(inStock < amount) {
            throw new IllegalArgumentException("Not enough in stock");
        }
        inStock -= amount;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commodity commodity = (Commodity) o;
        return id == commodity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, providerId, price);
    }
}