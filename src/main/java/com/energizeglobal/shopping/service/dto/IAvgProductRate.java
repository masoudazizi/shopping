package com.energizeglobal.shopping.service.dto;

import com.energizeglobal.shopping.domain.Product;

public interface IAvgProductRate {
    Product getProduct();
    Double getRate();
}