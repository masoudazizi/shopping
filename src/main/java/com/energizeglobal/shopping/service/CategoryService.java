package com.energizeglobal.shopping.service;

import com.energizeglobal.shopping.service.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO);

    Page<CategoryDTO> findAll(Pageable pageable);

    Optional<CategoryDTO> findOne(Long id);

    void delete(Long id);
}
