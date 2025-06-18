package github.pedrozaz.pricealert.service;

import github.pedrozaz.pricealert.dto.AlertRequest;
import github.pedrozaz.pricealert.entity.Alert;
import github.pedrozaz.pricealert.entity.Product;
import github.pedrozaz.pricealert.entity.User;
import github.pedrozaz.pricealert.repository.AlertRepository;
import github.pedrozaz.pricealert.repository.ProductRepository;
import github.pedrozaz.pricealert.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

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
        }
        else {

            String rawPrice = scrapperService.findPrice(request.getUrl(), request.getStore());
            if (rawPrice == null || rawPrice.isEmpty()) {
                throw new IllegalArgumentException("Failed to fetch product price from URL: " + request.getUrl());
            }

            String productName = scrapperService.findProductName(request.getUrl(), request.getStore());
            if (productName == null || productName.isEmpty()) {
                throw new IllegalArgumentException("Failed to fetch product name from URL: " + request.getUrl());
            }

            product = new Product();
            product.setName(productName);
            product.setUrl(request.getUrl());
            product.setCurrentPrice(parsePrice(rawPrice));
            product.setStore(request.getStore());

            product = productRepository.save(product);
            }

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseGet(() -> new User(request.getName(), request.getEmail()));

            user = userRepository.save(user);

            Optional<Alert> optionalAlert = alertRepository.findByProductIdAndUserId(product.getId(), user.getId());

            if (optionalAlert.isPresent()) {
                throw new IllegalArgumentException("Alert already exists for this product and user.");
            } else {
                Alert alert = new Alert(product, request.getTargetPrice(), request.getStore(), user);
                return alertRepository.save(alert);
            }
    }
}
