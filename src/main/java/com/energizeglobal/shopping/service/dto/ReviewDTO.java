package com.energizeglobal.shopping.service.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ReviewDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 500)
    private String comment;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer rate;

    private Long productId;

    private Long userId;
}
