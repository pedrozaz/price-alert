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

    public String cleanUrl(@NotNull String rawUrl) {
        String cleanedUrl = rawUrl;

        int queryIndex = cleanedUrl.indexOf('?');
        if (queryIndex != -1) {
            cleanedUrl = cleanedUrl.substring(0, queryIndex);
        }

        int fragmentIndex = cleanedUrl.indexOf('#');
        if (fragmentIndex != -1) {
            cleanedUrl = cleanedUrl.substring(0, fragmentIndex);
        }

        if (cleanedUrl.endsWith("/")) {
            cleanedUrl = cleanedUrl.substring(0, cleanedUrl.length() - 1);
        }
        return cleanedUrl;
    }

    public BigDecimal parsePrice(@NotNull String rawPrice) {
        try {
            String cleaned = rawPrice.replaceAll("[^\\d,\\.]", "")
                    .replace("\u00A0", "").
                    replaceAll("\\s+", "");
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

        String cleanedUrl = cleanUrl(request.getUrl());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseGet(() -> new User(request.getName(), request.getEmail()));

        Optional<Product> optionalProduct = productRepository.findByUrl(cleanedUrl);
        Product product = new Product();

        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {

            product.setUrl(cleanedUrl);
            product.setStore(scrapperService.findStoreName(cleanedUrl));

            String rawPrice = scrapperService.findPrice(cleanedUrl, product.getStore());
            if (rawPrice == null || rawPrice.isEmpty()) {
                throw new AlertException("Failed to fetch product price from URL: " + cleanedUrl);
            }

            String productName = scrapperService.findProductName(cleanedUrl, product.getStore());
            if (productName == null || productName.isEmpty()) {
                throw new AlertException("Failed to fetch product name from URL: " + cleanedUrl);
            }

            product.setName(productName);
            product.setCurrentPrice(parsePrice(rawPrice));

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
                alert.setStoreName(product.getStore());

                alert.setNotified(product.getCurrentPrice() !=
                        null && product.getCurrentPrice().compareTo(request.getTargetPrice()) <= 0);

                return alertRepository.save(alert);
            }
    }

    public Iterable<Alert> getAlertsByUserId(Long userId) {
        return alertRepository.findByUserId(userId);
    }

    public void deleteAlert(Long alertId) {
        Optional<Alert> optionalAlert = alertRepository.findById(alertId);
        if (optionalAlert.isPresent()) {
            alertRepository.delete(optionalAlert.get());
        } else {
            throw new AlertException("Alert not found with ID: " + alertId);
        }
    }
}
