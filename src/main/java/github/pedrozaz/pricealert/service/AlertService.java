package github.pedrozaz.pricealert.service;

import github.pedrozaz.pricealert.dto.AlertRequest;
import github.pedrozaz.pricealert.entity.Alert;
import github.pedrozaz.pricealert.entity.Product;
import github.pedrozaz.pricealert.repository.AlertRepository;
import github.pedrozaz.pricealert.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AlertService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private ScrapperService scrapperService;

    public BigDecimal parsePrice(String rawPrice) {
        try {
            String cleaned = rawPrice.replaceAll("[^\\d,\\.]", "");
            if (cleaned.contains(",") && cleaned.contains(".")) {
                cleaned = cleaned.replace(".", "").replace(",", ".");
            } else if (cleaned.contains(",")) {
                cleaned = cleaned.replace(",", ".");
            }
            return new BigDecimal(cleaned);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price format: " + rawPrice, e);
        }
    }

    public Alert createAlert(AlertRequest request) {

        Optional<Product> optionalProduct = productRepository.findByUrl(request.getUrl());
        Product product;

        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            String rawPrice = scrapperService.findPrice(request.getUrl(), request.getStore());
            if (rawPrice == null || rawPrice.isEmpty()) {
                throw new IllegalArgumentException("Failed to fetch product price from URL: " + request.getUrl());
            }
            BigDecimal price = parsePrice(rawPrice);
            product = new Product();
            product.setName("test");
            product.setUrl(request.getUrl());
            product.setCurrentPrice(price);
            product.setStore(request.getStore());
            product = productRepository.save(product);

            }
            Optional<Alert> optionalAlert = alertRepository.findByProductId(product.getId());

            if (optionalAlert.isPresent()) {
                throw new IllegalArgumentException("An alert for this product already exists.");
            } else {
                Alert alert = new Alert(product);
                return alertRepository.save(alert);
            }
    }
}
