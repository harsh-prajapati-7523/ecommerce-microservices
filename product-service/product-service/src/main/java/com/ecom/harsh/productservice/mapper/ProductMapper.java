package com.ecom.harsh.productservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import com.ecom.harsh.productservice.dto.ProductDTO;
import com.ecom.harsh.productservice.model.Product;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {
    ProductMapper INSTANCE=Mappers.getMapper(ProductMapper.class);
    
    Product toEntity(ProductDTO dto);
    ProductDTO toDTO(Product product);
    List<ProductDTO> toDtoList(List<Product> products);
    List<Product> toEntityList(List<ProductDTO> productDTOs);

}
