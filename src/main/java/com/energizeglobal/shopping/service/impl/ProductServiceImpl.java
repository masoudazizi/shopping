package com.energizeglobal.shopping.service.impl;

import com.energizeglobal.shopping.domain.Product;
import com.energizeglobal.shopping.repository.ProductRateRepository;
import com.energizeglobal.shopping.repository.ProductRepository;
import com.energizeglobal.shopping.service.ProductService;
import com.energizeglobal.shopping.service.dto.ProductDTO;
import com.energizeglobal.shopping.service.dto.ProductSearchDTO;
import com.energizeglobal.shopping.service.impl.sepecification.ProductSpecifications;
import com.energizeglobal.shopping.service.mapper.ProductMapper;
import com.energizeglobal.shopping.web.rest.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductRateRepository productRateRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productRepository.save(productMapper.toEntity(productDTO));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        if (!productRepository.existsById(productDTO.getId())) {
            throw new BadRequestException("error.id.is.notFound");
        }
        return save(productDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id).map(productMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        if (!productRepository.existsById(id)) {
            throw new BadRequestException("error.id.is.notFound");
        }
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductDTO> search(ProductSearchDTO productSearchDTO, Pageable pageable) {
        List<Integer> productIds = null;
        if (productSearchDTO != null && (productSearchDTO.getFromRate() != null || productSearchDTO.getToRate() != null)) {
            productIds = productRateRepository.getAllBetweenRates(productSearchDTO.getFromRate(), productSearchDTO.getToRate());
        }
        Specification<Product> specification = ProductSpecifications.createSpecification(productSearchDTO, productIds);
        return productRepository.findAll(specification, pageable)
                .map(productMapper::toDto);
    }
}
