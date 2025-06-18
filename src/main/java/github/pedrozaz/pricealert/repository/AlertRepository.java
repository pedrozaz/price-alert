package github.pedrozaz.pricealert.repository;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import github.pedrozaz.pricealert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findByProductId(Long productId);
}
