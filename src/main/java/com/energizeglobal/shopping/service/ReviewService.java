package com.energizeglobal.shopping.service;

import com.energizeglobal.shopping.service.dto.ReviewDTO;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    ReviewDTO save(ReviewDTO reviewDTO);

    ReviewDTO update(ReviewDTO reviewDTO);

    List<ReviewDTO> findAll();

    Optional<ReviewDTO> findOne(Long id);

    void delete(Long id);
}
