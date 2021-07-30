package com.energizeglobal.shopping.web.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InternalServerException extends RuntimeException {
    private String errorCode;
}
