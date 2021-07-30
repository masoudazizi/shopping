package com.energizeglobal.shopping.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class WebConfigurer {

    private final static Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final ApplicationProperties applicationProperties;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = applicationProperties.getCors();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
        }
        return new CorsFilter(source);
    }
}
