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

    private static final Map<String, String> PRODUCT_NAME_SELECTORS = Map.of(
            "kabum", "h1.brTtKt",
            "amazon", "span#productTitle",
            "mercadolivre", "h1.ui-pdp-title"
    );

    public String getPriceSelector(String storeName) {
        String selector = PRICE_SELECTORS.get(storeName);
        if (selector == null) {
            throw new IllegalArgumentException("Store not supported: " + storeName);
        }
        return selector;
    }

    public String getProductNameSelector(String storeName) {
        String selector = PRODUCT_NAME_SELECTORS.get(storeName);
        if (selector == null) {
            throw new IllegalArgumentException("Store not supported: " + storeName);
        }
        return selector;
    }
}
