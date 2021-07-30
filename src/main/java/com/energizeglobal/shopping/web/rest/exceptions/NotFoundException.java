package com.energizeglobal.shopping.web.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private String errorCode;
}
