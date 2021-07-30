package com.energizeglobal.shopping.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
@Table(name = "product_rate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRate implements BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private Long productId;

    private Double rate;
}
