package com.energizeglobal.shopping.service.mapper;

import com.energizeglobal.shopping.domain.Product;
import com.energizeglobal.shopping.service.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryTitle")
    ProductDTO toDto(Product s);

    @Mapping(source = "categoryId", target = "category")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
