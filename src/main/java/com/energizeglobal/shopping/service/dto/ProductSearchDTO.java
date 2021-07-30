package com.energizeglobal.shopping.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductSearchDTO implements Serializable {
    private String title;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
    private String categoryTitle;
    private Double fromRate = 1d;
    private Double toRate = 5d;
}
