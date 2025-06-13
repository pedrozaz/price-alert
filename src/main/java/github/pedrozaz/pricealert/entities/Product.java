package github.pedrozaz.pricealert.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product {
    private String productName;
    private String productUrl;
    private String currentPrice;
    private Double desiredPrice;

    public Product() {
    }

    public Product(String productName, String productUrl, String currentPrice, Double desiredPrice) {
        this.productName = productName;
        this.productUrl = productUrl;
        this.currentPrice = currentPrice;
        this.desiredPrice = desiredPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", currentPrice=" + currentPrice +
                ", desiredPrice=" + desiredPrice +
                '}';
    }
}
