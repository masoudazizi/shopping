package com.energizeglobal.shopping.web.rest;

import com.energizeglobal.shopping.config.Constants;
import com.energizeglobal.shopping.repository.ProductRepository;
import com.energizeglobal.shopping.repository.ReviewRepository;
import com.energizeglobal.shopping.service.ProductService;
import com.energizeglobal.shopping.service.dto.IAvgProductRate;
import com.energizeglobal.shopping.service.dto.ProductDTO;
import com.energizeglobal.shopping.service.dto.ProductSearchDTO;
import com.energizeglobal.shopping.web.rest.exceptions.BadRequestException;
import com.energizeglobal.shopping.web.util.PaginationUtil;
import com.energizeglobal.shopping.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductResource {

    private final static Logger log = LoggerFactory.getLogger(ProductResource.class);
    private final ProductService productService;
    private final ReviewRepository reviewRepository;

    @PostMapping("/products")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", productDTO);
        if (productDTO.getId() != null) {
            throw new BadRequestException("error.entity.has.id");
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity
                .created(new URI("/api/products/" + result.getId()))
                .body(result);
    }

    @PutMapping("/products")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            throw new BadRequestException("error.id.is.invalid");
        }
        ProductDTO result = productService.update(productDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable) {
        log.debug("REST request to get a page of Products");
        Page<ProductDTO> page = productService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        Optional<ProductDTO> productDTO = productService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDTO);
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(value = "/products/search")
    @PreAuthorize("hasAuthority(\"" + Constants.USER + "\")")
    public ResponseEntity<List<ProductDTO>> findAllBySpecification(@RequestBody ProductSearchDTO productSearchDTO, Pageable pageable) {
        Page<ProductDTO> page = productService.search(productSearchDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
