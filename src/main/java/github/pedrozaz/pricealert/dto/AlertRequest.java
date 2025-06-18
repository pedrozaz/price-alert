package github.pedrozaz.pricealert.dto;

import github.pedrozaz.pricealert.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AlertRequest {
    private String name;
    private String email;
    private String url;
    private BigDecimal targetPrice;
    private String store;
}
