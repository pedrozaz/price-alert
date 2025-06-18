package github.pedrozaz.pricealert.service;

import github.pedrozaz.pricealert.exception.StoreSelectorException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StoreSelectorService {
    private static final Map<String, String> PRICE_SELECTORS = Map.of(
            "kabum", "h4.finalPrice",
            "amazon", "span.a-price-whole",
            "mercadolivre", "div.ui-pdp-price__second-line span.andes-money-amount__fraction",
            "americanas", "div.ProductPrice_productPrice__vpgdo"

    );

    private static final Map<String, String> PRODUCT_NAME_SELECTORS = Map.of(
            "kabum", "h1.brTtKt",
            "amazon", "span#productTitle",
            "mercadolivre", "h1.ui-pdp-title",
            "americanas", "h1.ProductInfoCenter_title__hdTX_"
    );

    public String getPriceSelector(String storeName) {
        String selector = PRICE_SELECTORS.get(storeName);
        if (selector == null) {
            throw new StoreSelectorException("Store not supported: " + storeName);
        }
        return selector;
    }

    public String getProductNameSelector(String storeName) {
        String selector = PRODUCT_NAME_SELECTORS.get(storeName);
        if (selector == null) {
            throw new StoreSelectorException("Store not supported: " + storeName);
        }
        return selector;
    }
}
