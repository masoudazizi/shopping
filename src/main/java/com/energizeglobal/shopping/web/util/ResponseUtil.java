package com.energizeglobal.shopping.web.util;

import com.energizeglobal.shopping.web.rest.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;

import java.util.Optional;

public interface ResponseUtil {
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, (HttpHeaders) null);
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return (ResponseEntity) maybeResponse.map((response) -> {
            return ((BodyBuilder) ResponseEntity.ok().headers(header)).body(response);
        }).orElseThrow(() -> {
            return new NotFoundException("error.entity.is.not.found");
        });
    }
}

