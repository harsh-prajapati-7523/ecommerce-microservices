package com.ecom.harsh.productservice.service;


import com.ecom.harsh.productservice.dto.ProductDTO;
import com.ecom.harsh.productservice.mapper.ProductMapper;
import com.ecom.harsh.productservice.model.Product;
import com.ecom.harsh.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    // Constructor Injection (recommended over @Autowired field injection)
    public ProductService(ProductRepository productRepository,ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper =productMapper;
    }



    // Get all products
    public List<ProductDTO> getAllProducts() {
        log.debug("Fetching all products from database");
        List<Product> products = productRepository.findAll();
        log.info("Total products fetched from database: {}", products);  
        return productMapper.toDtoList(products);
    }

    // Get product by ID
    @Cacheable(value = "products", key = "#id")
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toDTO);
    }

    // Create new product
    @CachePut(value = "products", key = "#result.id")
    public ProductDTO createProduct(ProductDTO productDTO) {
        return productMapper.toDTO(productRepository.save(productMapper.toEntity(productDTO)));
    }

    // Update existing product
    // public Optional<Product> updateProduct(Long id, Product updatedProduct) {
    //     return productRepository.findById(id).map(existingProduct -> {
    //         existingProduct.setName(updatedProduct.getName());
    //         existingProduct.setDescription(updatedProduct.getDescription());
    //         existingProduct.setPrice(updatedProduct.getPrice());
    //         existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
    //         return productRepository.save(existingProduct);
    //     });
    // }
    // Update only Given details in existing product
    @CacheEvict(value = "products", key = "#id")
    public Optional<ProductDTO> updateProduct(Long id, ProductDTO updatedProductDTO) {

        return productRepository.findById(id).map(existingProduct -> {
            if (updatedProductDTO.getName() != null || updatedProductDTO.getName() == null) {
                existingProduct.setName(updatedProductDTO.getName());
            }
            if (updatedProductDTO.getDescription() != null || updatedProductDTO.getDescription() == null) {
                existingProduct.setDescription(updatedProductDTO.getDescription());
            }
            if (updatedProductDTO.getPrice() != null) {
                existingProduct.setPrice(updatedProductDTO.getPrice());
            }
            if (updatedProductDTO.getStockQuantity() != null) {
                existingProduct.setStockQuantity(updatedProductDTO.getStockQuantity());
            }
            return productRepository.save(existingProduct);
        }).map(productMapper::toDTO);
    }


    // Delete product by ID
    @CacheEvict(value = "products", key = "#id")
    public boolean deleteProduct(Long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return true;
        }).orElse(false);
    }

    @Cacheable(value = "productsByName", key = "#name.toLowerCase()")
    public List<ProductDTO> searchProductsByName(String name) {
    return productMapper.toDtoList(productRepository.findByNameContainingIgnoreCase(name));
    }

    public List<ProductDTO> filterProductsByPrice(double minPrice, double maxPrice) {
        return productMapper.toDtoList(productRepository.findByPriceBetween(minPrice, maxPrice));
    }

    public Page<ProductDTO> searchProductsByNamePaginated(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContainingIgnoreCase(name, pageable).map(productMapper::toDTO);
    }

    public List<ProductDTO> createProductsBulk(List<Product> products) {
    return productMapper.toDtoList(productRepository.saveAll(products));
}

    public String searchSortProduct(Sort sort, Pageable pageable) {

        throw new UnsupportedOperationException("Unimplemented method 'searchSortProduct'");
    }

}

