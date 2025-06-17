package github.pedrozaz.pricealert.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private String productName;
    private String productURL;
    private String productPrice;

    public ProductDTO(String productName, String productURL, String productPrice) {
        this.productName = productName;
        this.productURL = productURL;
        this.productPrice = productPrice;
    }
}
