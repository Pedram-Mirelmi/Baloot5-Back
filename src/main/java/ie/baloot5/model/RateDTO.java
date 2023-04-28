package ie.baloot5.model;

public class RateDTO {
    private final float rating;
    private final String status;
    private final long rateCount;

    public float getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }


    public long getRateCount() {
        return rateCount;
    }
    public RateDTO(String status, float rating, long commodityRateCount) {
        this.rating = rating;
        this.status = status;
        this.rateCount = commodityRateCount;
    }
}
