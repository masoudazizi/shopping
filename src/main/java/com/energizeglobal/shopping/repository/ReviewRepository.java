package com.energizeglobal.shopping.repository;

import com.energizeglobal.shopping.domain.Review;
import com.energizeglobal.shopping.service.dto.IAvgProductRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r.product AS product, AVG(r.rate) AS rate "
            + "FROM Review AS r GROUP BY r.product.id ORDER BY r.product.id DESC")
    List<IAvgProductRate> averageOfProductRate();
}
