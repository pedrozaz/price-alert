package github.pedrozaz.pricealert.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StoreSelectorService {
    private static final Map<String, String> PRICE_SELECTORS = Map.of(
            "kabum", "h4.finalPrice",
            "amazon", "span.a-price-whole",
            "mercadolivre", "div.ui-pdp-price__second-line span.andes-money-amount__fraction"
    );

    public String getPriceSelector(String storeName) {
        String selector = PRICE_SELECTORS.get(storeName);
        if (selector == null) {
            throw new IllegalArgumentException("Store not supported: " + storeName);
        }
        return selector;
    }
}
