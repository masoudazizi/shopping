package com.energizeglobal.shopping.repository;

import com.energizeglobal.shopping.domain.ProductRate;
import com.energizeglobal.shopping.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRateRepository extends JpaRepository<ProductRate, Long> {

    @Query(value = "select pt.productId from ProductRate pt where pt.rate BETWEEN :startRate AND :endRate")
    List<Integer> getAllBetweenRates(@Param("startRate")Double startRate, @Param("endRate")Double endRate);}
