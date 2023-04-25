package ie.baloot5.model;

public class ShoppingItem {
    private long commodityId;
    private String commodityName;
    private long count;
    private long inStock;
    private float rating;

    public ShoppingItem(Commodity commodity, long count) {
        this.commodityId = commodity.getId();
        this.commodityName = commodity.getName();
        this.inStock = commodity.getInStock();
        this.rating = commodity.getRating();
        this.count = count;
    }

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


}
