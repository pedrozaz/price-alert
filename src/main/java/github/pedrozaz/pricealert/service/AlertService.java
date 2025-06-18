package github.pedrozaz.pricealert.service;

import github.pedrozaz.pricealert.dto.AlertRequest;
import github.pedrozaz.pricealert.entity.Alert;
import github.pedrozaz.pricealert.entity.Product;
import github.pedrozaz.pricealert.entity.User;
import github.pedrozaz.pricealert.exception.AlertException;
import github.pedrozaz.pricealert.repository.AlertRepository;
import github.pedrozaz.pricealert.repository.ProductRepository;
import github.pedrozaz.pricealert.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public BigDecimal parsePrice(@NotNull String rawPrice) {
        try {
            String cleaned = rawPrice.replaceAll("[^\\d,\\.]", "");
            if (cleaned.contains(",") && cleaned.contains(".")) {
                cleaned = cleaned.replace(".", "").replace(",", ".");
            } else if (cleaned.contains(",")) {
                cleaned = cleaned.replace(",", ".");
            } else if (cleaned.contains(".")) {
                cleaned = cleaned.replace(".", "");
            }
            return new BigDecimal(cleaned);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price format: " + rawPrice, e);
        }
    }

    public Alert createAlert(@NotNull AlertRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseGet(() -> new User(request.getName(), request.getEmail()));

        Optional<Product> optionalProduct = productRepository.findByUrl(request.getUrl());
        Product product;

        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {

            String rawPrice = scrapperService.findPrice(request.getUrl(), request.getStore());
            if (rawPrice == null || rawPrice.isEmpty()) {
                throw new AlertException("Failed to fetch product price from URL: " + request.getUrl());
            }

            String productName = scrapperService.findProductName(request.getUrl(), request.getStore());
            if (productName == null || productName.isEmpty()) {
                throw new AlertException("Failed to fetch product name from URL: " + request.getUrl());
            }

            product = new Product();

            product.setName(productName);
            product.setUrl(request.getUrl());
            product.setCurrentPrice(parsePrice(rawPrice));
            product.setStore(request.getStore());

            }

            product = productRepository.save(product);
            user = userRepository.save(user);

            Optional<Alert> optionalAlert = alertRepository.findByProductIdAndUserId(product.getId(), user.getId());

            if (optionalAlert.isPresent()) {
                throw new AlertException("This alert already exists for this user.");
            } else {
                String now = LocalDateTime.now().format(dateFormat);
                LocalDateTime formattedLocalDate = LocalDateTime.parse(now, dateFormat);

                Alert alert = new Alert();

                alert.setProduct(product);
                alert.setUser(user);
                alert.setTargetPrice(request.getTargetPrice());
                alert.setCreatedAt(formattedLocalDate);
                alert.setStoreName(request.getStore());

                alert.setNotified(product.getCurrentPrice() !=
                        null && product.getCurrentPrice().compareTo(request.getTargetPrice()) <= 0);

                return alertRepository.save(alert);
            }
    }
}
