package github.pedrozaz.pricealert.repository;

import github.pedrozaz.pricealert.entity.ProductPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<ProductPriceHistory, Long> {
}
