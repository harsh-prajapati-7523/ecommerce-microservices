package com.ecom.harsh.productservice.repository;

import com.ecom.harsh.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom query methods here later, e.g.:
    // List<Product> findByNameContaining(String keyword);
        // Search by name containing a string (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Search by price range
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // Pagination example
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
