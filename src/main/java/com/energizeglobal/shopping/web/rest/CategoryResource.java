package com.energizeglobal.shopping.web.rest;

import com.energizeglobal.shopping.config.Constants;
import com.energizeglobal.shopping.service.CategoryService;
import com.energizeglobal.shopping.service.dto.CategoryDTO;
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
public class CategoryResource {

    private final static Logger log = LoggerFactory.getLogger(CategoryResource.class);
    private final CategoryService categoryService;

    @PostMapping("/categories")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws URISyntaxException {
        log.debug("REST request to save Category : {}", categoryDTO);
        if (categoryDTO.getId() != null) {
            throw new BadRequestException("error.entity.has.id");
        }
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity
                .created(new URI("/api/categories/" + result.getId()))
                .body(result);
    }

    @PutMapping("/categories")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        log.debug("REST request to update Category : {}", categoryDTO);
        if (categoryDTO.getId() == null) {
            throw new BadRequestException("error.id.is.invalid");
        }
        CategoryDTO result = categoryService.update(categoryDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }


    @GetMapping("/categories")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(Pageable pageable) {
        log.debug("REST request to get a page of Categories");
        Page<CategoryDTO> page = categoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent());
    }


    @GetMapping("/categories/{id}")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryDTO);
    }


    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
