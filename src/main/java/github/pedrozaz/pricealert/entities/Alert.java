package github.pedrozaz.pricealert.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;



public class Alert {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    private Product product;

    public Alert(Long id, Product product) {
        this.id = id;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", product=" + product +
                '}';
    }
}
