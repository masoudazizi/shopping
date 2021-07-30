package com.energizeglobal.shopping.web.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadRequestException extends RuntimeException {
    private String errorCode;
}
