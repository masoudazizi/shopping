package com.energizeglobal.shopping.web.rest;

import com.energizeglobal.shopping.config.Constants;
import com.energizeglobal.shopping.service.ReviewService;
import com.energizeglobal.shopping.service.dto.ReviewDTO;
import com.energizeglobal.shopping.web.rest.exceptions.BadRequestException;
import com.energizeglobal.shopping.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewResource {

    private final static Logger log = LoggerFactory.getLogger(ReviewResource.class);

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    @PreAuthorize("hasAuthority(\"" + Constants.USER + "\")")
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) throws URISyntaxException {
        log.debug("REST request to save Review : {}", reviewDTO);
        if (reviewDTO.getId() != null) {
            throw new BadRequestException("error.entity.has.id");
        }
        ReviewDTO result = reviewService.save(reviewDTO);
        return ResponseEntity
                .created(new URI("/api/reviews/" + result.getId()))
                .body(result);
    }

    @PutMapping("/reviews")
    @PreAuthorize("hasAuthority(\"" + Constants.USER + "\")")
    public ResponseEntity<ReviewDTO> updateReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        log.debug("REST request to update Review : {}", reviewDTO);
        if (reviewDTO.getId() == null) {
            throw new BadRequestException("error.id.is.invalid");
        }
        ReviewDTO result = reviewService.update(reviewDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }


    @GetMapping("/reviews")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public List<ReviewDTO> getAllReviews() {
        log.debug("REST request to get all Reviews");
        return reviewService.findAll();
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        log.debug("REST request to get Review : {}", id);
        Optional<ReviewDTO> reviewDTO = reviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reviewDTO);
    }

    @DeleteMapping("/reviews/{id}")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        log.debug("REST request to delete Review : {}", id);
        reviewService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
