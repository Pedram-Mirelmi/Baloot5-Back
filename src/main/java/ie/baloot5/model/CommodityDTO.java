package ie.baloot5.model;

public class CommodityDTO extends Commodity {
    final private long inCart;
    final private long rateCount;
    final private String providerName;

    public CommodityDTO(Commodity commodity, long inCart, long ratingCount, String providerName) {
        super(commodity);
        this.inCart = inCart;
        this.rateCount = ratingCount;
        this.providerName = providerName;
    }

    public long getInCart() {
        return inCart;
    }

    public long getRateCount() {
        return rateCount;
    }

    public String getProviderName() {
        return providerName;
    }
}
