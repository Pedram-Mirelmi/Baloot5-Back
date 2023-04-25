package ie.baloot5.model;

import com.google.gson.annotations.SerializedName;

public class Discount {
    @SerializedName("discountCode")
    private String discountCode;

    @SerializedName("discount")
    private int discount;

    public Discount(String discountCode, int discount) {
        this.discountCode = discountCode;
        this.discount = discount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
