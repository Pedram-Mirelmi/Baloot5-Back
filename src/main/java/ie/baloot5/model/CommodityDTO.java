package ie.baloot5.model;

public class CommodityDTO extends Commodity {
    final private long inCart;
    final private long rateCount;

    public CommodityDTO(Commodity commodity, long inCart, long ratingCount) {
        super(commodity);
        this.inCart = inCart;
        this.rateCount = ratingCount;
    }

    public long getInCart() {
        return inCart;
    }

    public long getRateCount() {
        return rateCount;
    }
}
