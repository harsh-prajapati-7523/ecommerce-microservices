package com.ecom.harsh.productservice.controller;

import com.ecom.harsh.productservice.dto.ProductDTO;
import com.ecom.harsh.productservice.model.Product;
import com.ecom.harsh.productservice.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log=LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    // Constructor Injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ Get all products
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        log.debug("Fetching all products");
        List<ProductDTO> products= productService.getAllProducts();
        log.info("Total products fetched: {}", products);
        return products;
    }

    // ✅ Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> productDTO = productService.getProductById(id);
        return productDTO.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Create new product
    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    // ✅ Update existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductDTO productDTO) {
        Optional<ProductDTO> updated = productService.updateProduct(id, productDTO);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchProductsByName(name));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductDTO>> filterProducts(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        return ResponseEntity.ok(productService.filterProductsByPrice(minPrice, maxPrice));
    }

    @GetMapping("/search/paginated")
    public ResponseEntity<Page<ProductDTO>> searchProductsPaginated(
            @RequestParam String name,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(productService.searchProductsByNamePaginated(name, page, size));
    }

    @PostMapping("/bulkCreate")
    public ResponseEntity<List<ProductDTO>> createProductsBulk(@RequestBody List<@Valid Product> products) {
        List<ProductDTO> savedProductsDTO = productService.createProductsBulk(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductsDTO);
    }

    @GetMapping("/searchByName")
    public String getMethodName(
        @RequestParam(required = false) String name,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir

    ) {
        Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(page, size, sort);
        return productService.searchSortProduct(sort,pageable);
    }
    

}
