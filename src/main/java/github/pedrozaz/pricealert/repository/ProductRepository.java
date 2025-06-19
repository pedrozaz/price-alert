package github.pedrozaz.pricealert.repository;

import github.pedrozaz.pricealert.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUrl(String url);
    @Query("SELECT p FROM Product p WHERE p.alerts IS EMPTY")
    List<Product> findProductsWithoutAlerts();
}
