package github.pedrozaz.pricealert.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Alert implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private BigDecimal targetPrice;
    private LocalDateTime createdAt;
    private Boolean notified;
    private String storeName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Alert() {
    }

    public Alert(Product product, BigDecimal targetPrice, String storeName, User user) {
        this.product = product;
        this.targetPrice = targetPrice;
        this.storeName = storeName;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return Objects.equals(id, alert.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
