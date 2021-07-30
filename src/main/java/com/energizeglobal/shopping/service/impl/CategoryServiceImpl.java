package com.energizeglobal.shopping.service.impl;

import com.energizeglobal.shopping.domain.Category;
import com.energizeglobal.shopping.repository.CategoryRepository;
import com.energizeglobal.shopping.service.CategoryService;
import com.energizeglobal.shopping.service.dto.CategoryDTO;
import com.energizeglobal.shopping.service.mapper.CategoryMapper;
import com.energizeglobal.shopping.web.rest.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final static Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryRepository.save(categoryMapper.toEntity(categoryDTO));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        if (!categoryRepository.existsById(categoryDTO.getId())) {
            throw new BadRequestException("error.id.is.notFound");
        }
        return save(categoryDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new BadRequestException("error.id.is.notFound");
        }
        categoryRepository.deleteById(id);
    }
}
