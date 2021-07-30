package com.energizeglobal.shopping.service.impl;

import com.energizeglobal.shopping.domain.Review;
import com.energizeglobal.shopping.repository.ReviewRepository;
import com.energizeglobal.shopping.repository.UserRepository;
import com.energizeglobal.shopping.security.SecurityUtils;
import com.energizeglobal.shopping.service.ReviewService;
import com.energizeglobal.shopping.service.dto.ReviewDTO;
import com.energizeglobal.shopping.service.mapper.ReviewMapper;
import com.energizeglobal.shopping.web.rest.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final static Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;

    @Override
    public ReviewDTO save(ReviewDTO reviewDTO) {
        log.debug("Request to save Review : {}", reviewDTO);
        Review review = reviewMapper.toEntity(reviewDTO);
        if (SecurityUtils.getCurrentUserLogin().isPresent()) {
            userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get())
                    .ifPresent(user -> {
                        review.setUser(user);
                    });
        }
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public ReviewDTO update(ReviewDTO reviewDTO) {
        log.debug("Request to update Review : {}", reviewDTO);
        if (!reviewRepository.existsById(reviewDTO.getId())) {
            throw new BadRequestException("error.id.is.notFound");
        }
        return save(reviewDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        log.debug("Request to get all Reviews");
        return reviewRepository
                .findAll()
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<ReviewDTO> findAllWithEagerRelationships(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewDTO> findOne(Long id) {
        log.debug("Request to get Review : {}", id);
        return reviewRepository.findById(id).map(reviewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        if (!reviewRepository.existsById(id)) {
            throw new BadRequestException("error.id.is.notFound");
        }
        reviewRepository.deleteById(id);
    }
}
