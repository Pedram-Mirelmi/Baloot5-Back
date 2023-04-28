package ie.baloot5.model;

import java.util.List;

public class ShoppingItem {
    final private long commodityId;
    final private String commodityName;
    final private long providerId;
    final private long count;
    final private long price;
    final private List<String> categories;
    final private long inStock;
    final private float rating;

    public ShoppingItem(Commodity commodity, long count) {
        this.commodityId = commodity.getId();
        this.price = commodity.getPrice();
        this.categories = commodity.getCategories();
        this.providerId = commodity.getProviderId();
        this.commodityName = commodity.getName();
        this.inStock = commodity.getInStock();
        this.rating = commodity.getRating();
        this.count = count;
    }

    public long getCommodityId() {
        return commodityId;
    }


    public String getCommodityName() {
        return commodityName;
    }


    public long getCount() {
        return count;
    }


    public long getInStock() {
        return inStock;
    }


    public float getRating() {
        return rating;
    }

    public long getPrice() {
        return price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public long getProviderId() {
        return providerId;
    }
}
