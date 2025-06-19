package github.pedrozaz.pricealert.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    private String name;

    @Setter
    @Column(nullable = false, unique = true)
    private String url;

    @Setter
    private BigDecimal currentPrice;
    @Setter
    private String store;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Alert> alerts = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProductPriceHistory> priceHistory = new ArrayList<>();

    public Product() {
    }

    public Product(Long id, String name, String url,
                   BigDecimal currentPrice) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.currentPrice = currentPrice;
    }

    public void addHistory(BigDecimal price) {
        ProductPriceHistory newHistory= new ProductPriceHistory(price.toString(), LocalDateTime.now(), this);
        this.priceHistory.add(newHistory);
        this.setCurrentPrice(price);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
