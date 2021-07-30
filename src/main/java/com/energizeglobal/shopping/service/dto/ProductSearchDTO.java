package com.energizeglobal.shopping.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductSearchDTO implements Serializable {
    private String title;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
    private String categoryTitle;
    private Double fromRate;
    private Double toRate;
}
