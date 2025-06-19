package github.pedrozaz.pricealert.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "price_history")
public class ProductPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String price;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductPriceHistory() {
    }

    public ProductPriceHistory(String price, LocalDateTime dateTime, Product product) {
        this.price = price;
        this.dateTime = dateTime;
        this.product = product;
    }
}
