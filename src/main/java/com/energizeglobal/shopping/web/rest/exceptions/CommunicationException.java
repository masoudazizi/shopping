package com.energizeglobal.shopping.web.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommunicationException extends RuntimeException {
    private String errorCode;
}
