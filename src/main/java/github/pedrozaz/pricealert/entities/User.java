package github.pedrozaz.pricealert.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class User {
    @Getter
    @Setter
    private String email;

    @Getter
    private List<Product> products;

    public User(String email, List<Product> products) {
        this.email = email;
        this.products = products;
    }
}
