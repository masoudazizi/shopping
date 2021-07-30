package com.energizeglobal.shopping.config;

import com.energizeglobal.shopping.domain.ProductRate;
import com.energizeglobal.shopping.repository.ProductRateRepository;
import com.energizeglobal.shopping.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class ProductRateSchedulerConfiguration {

    private final ReviewRepository reviewRepository;
    private final ProductRateRepository productRateRepository;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void updateTenantTitleMapper() {
        log.info("########### product Rate Scheduler has been updated by scheduler ###########");
        reviewRepository.averageOfProductRate().stream()
                .forEach(iAvgProductRate -> {
                    productRateRepository.saveAndFlush(new ProductRate(iAvgProductRate.getProduct().getId(), iAvgProductRate.getRate()));
                });
    }
}
