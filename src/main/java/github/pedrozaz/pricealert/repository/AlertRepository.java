package github.pedrozaz.pricealert.repository;

import github.pedrozaz.pricealert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findByProductIdAndUserId(Long productId, Long userId);
    Iterable<Alert> findByUserId(Long userId);
}
