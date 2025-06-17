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
public class Alert implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Setter
    private BigDecimal targetPrice;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private Boolean notified;

    public Alert() {
    }

    public Alert(Product product) {
        this.product = product;
    }

    public Alert(Long id, Product product,
                  BigDecimal targetPrice,
                 LocalDateTime createdAt, Boolean notified) {
        this.id = id;
        this.product = product;
        this.targetPrice = targetPrice;
        this.createdAt = createdAt;
        this.notified = notified;
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

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", product=" + product +
                ", targetPrice=" + targetPrice +
                ", createdAt=" + createdAt +
                ", notified=" + notified +
                '}';
    }
}
