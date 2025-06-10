package github.pedrozaz.pricealert.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {
    private String productName;
    private String productUrl;
    private Double currentPrice;
    private Double desiredPrice;

    public Product() {
    }

    public Product(String productName, String productUrl, Double currentPrice, Double desiredPrice) {
        this.productName = productName;
        this.productUrl = productUrl;
        this.currentPrice = currentPrice;
        this.desiredPrice = desiredPrice;
    }
}
