package com.energizeglobal.shopping.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDTO implements Serializable {
    private Long id;
    @NotNull
    @Size(max = 100)
    private String title;
    @Size(max = 500)
    private String description;
    @NotNull
    private BigDecimal price;
    private Long categoryId;
    private String categoryTitle;
}
