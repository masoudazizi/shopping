package com.energizeglobal.shopping.service.mapper;

import com.energizeglobal.shopping.domain.Review;
import com.energizeglobal.shopping.service.dto.ReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, UserMapper.class})
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "user.id", target = "userId")
    ReviewDTO toDto(Review s);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "userId", target = "user")
    Review toEntity(ReviewDTO reviewDTO);

    default Review fromId(Long id) {
        if (id == null) {
            return null;
        }
        Review review = new Review();
        review.setId(id);
        return review;
    }
}
