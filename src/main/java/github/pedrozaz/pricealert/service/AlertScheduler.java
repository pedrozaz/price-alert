package github.pedrozaz.pricealert.service;

import github.pedrozaz.pricealert.entity.Alert;
import github.pedrozaz.pricealert.repository.AlertRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public AlertScheduler(AlertRepository alertRepository, ScrapperService scrapperService,
                          AlertService alertService) {
        this.alertRepository = alertRepository;
        this.scrapperService = scrapperService;
        this.alertService = alertService;
    }

    @Scheduled(fixedRate = 20000)
    public void checkAlerts() {
        logger.info("Checking alerts...");

        List<Alert> alertList = alertRepository.findAll();
        int triggeredCount = 0;

        for (Alert alert : alertList) {
          try {
              String priceStr = scrapperService.findPrice(alert.getProduct().getUrl(), alert.getStoreName());
              BigDecimal currentPrice = alertService.parsePrice(priceStr);
              BigDecimal targetPrice = alert.getTargetPrice();

              if (currentPrice.compareTo(targetPrice) <= 0) {
                  alert.setNotified(true);
                  alert.getProduct().setCurrentPrice(currentPrice);
                  alertRepository.save(alert);

                  logger.info("Alert triggered for product: {} | Current price: {} | Target price: {} | User: {}",
                          alert.getProduct().getUrl(),
                          currentPrice,
                          targetPrice,
                          alert.getUser().getEmail());

                  emailService.sendAlertEmail(alert.getUser().getEmail(),
                          alert.getProduct().getName(),
                          currentPrice.toString(),
                          alert.getProduct().getUrl());

                  triggeredCount++;
                  alertRepository.delete(alert);

              } else {
                  logger.info("No alert triggered for product: {} | Current price: {} | Target price: {} | Notified: {}",
                          alert.getProduct().getUrl(),
                          currentPrice,
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
}
