package com.energizeglobal.shopping.web.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private String requestDateTime;
    private HttpStatus status;
    private String detail;
    private String message;
    private Map<String, Object> parameters;
}