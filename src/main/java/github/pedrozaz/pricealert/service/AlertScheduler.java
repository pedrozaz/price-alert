package github.pedrozaz.pricealert.service;

import github.pedrozaz.pricealert.entity.Alert;
import github.pedrozaz.pricealert.entity.Product;
import github.pedrozaz.pricealert.repository.AlertRepository;
import github.pedrozaz.pricealert.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertScheduler {

    private static final Logger logger = LoggerFactory.getLogger(AlertScheduler.class);

    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private ScrapperService scrapperService;
    @Autowired
    private AlertService alertService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public AlertScheduler(AlertRepository alertRepository, ScrapperService scrapperService,
                          AlertService alertService) {
        this.alertRepository = alertRepository;
        this.scrapperService = scrapperService;
        this.alertService = alertService;
    }

    @Scheduled(fixedRate = 20000)
    @Transactional
    public void checkAlerts() {
        logger.info("Checking alerts...");

        List<Alert> alertList = alertRepository.findAll();
        List<Product> productsList = productRepository.findAll();
        int triggeredCount = 0;

        for (Alert alert : alertList) {
          try {

              BigDecimal currentPrice = alert.getProduct().getCurrentPrice();
              String priceStr = scrapperService.findPrice(alert.getProduct().getUrl(), alert.getStoreName());
              BigDecimal newPrice = alertService.parsePrice(priceStr);
              BigDecimal targetPrice = alert.getTargetPrice();

              if (newPrice != null && !newPrice.equals(currentPrice)) {
                  logger.info("Price updated for product: {} | New price: {} | Previous price: {}",
                          alert.getProduct().getName(), newPrice, currentPrice);
                  alert.getProduct().addHistory(newPrice);
              }

              assert newPrice != null;
              if (newPrice.compareTo(targetPrice) <= 0) {
                  alert.setNotified(true);
                  alert.getProduct().setCurrentPrice(newPrice);
                  alertRepository.save(alert);

                  logger.info("Alert triggered for product: {} | Current price: {} | Target price: {} | User: {}",
                          alert.getProduct().getUrl(),
                          newPrice,
                          targetPrice,
                          alert.getUser().getEmail());

                  emailService.sendAlertEmail(
                          alert.getUser().getEmail(),
                          alert.getProduct().getName(),
                          newPrice.toString(),
                          targetPrice.toString(),
                          alert.getProduct().getUrl(),
                          alert.getStoreName(),
                          LocalDateTime.now());

                  triggeredCount++;
                  alertRepository.delete(alert);

              } else {
                  logger.info("No alert triggered for product: {} | Current price: {} | Target price: {} | Notified: {}",
                          alert.getProduct().getUrl(),
                          newPrice,
                          targetPrice,
                          alert.getNotified());
              }
          } catch (Exception e) {
                logger.error("Error checking alert for product: {} | Error: {}",
                        alert.getProduct().getName(), e.getMessage());
          }
      }
        logger.info("Finished checking alerts. Total triggered: {}", triggeredCount);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void deleteProducts() {
        logger.info("Deleting products without alerts...");

        List<Product> deletingProductsList= productRepository.findProductsWithoutAlerts();

        if (!deletingProductsList.isEmpty()) {
            logger.info("Found {} products without alerts to delete.", deletingProductsList.size());
            productRepository.deleteAll(deletingProductsList);
            logger.info("Deleted {} products without alerts.", deletingProductsList.size());
        } else {
            logger.info("No products without alerts found to delete.");
        }
    }
}
