package com.energizeglobal.shopping.service;

import com.energizeglobal.shopping.domain.Product;
import com.energizeglobal.shopping.service.dto.ProductDTO;
import com.energizeglobal.shopping.service.dto.ProductSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductDTO save(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    Page<ProductDTO> findAll(Pageable pageable);

    Optional<ProductDTO> findOne(Long id);

    void delete(Long id);

    Page<ProductDTO> search(ProductSearchDTO productSearchDTO, Pageable pageable);
}
