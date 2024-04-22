package kea3.delivery.product;

import kea3.delivery.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findById(Long integer);

    Optional<Product> findByNameIgnoreCase(String name);
}
