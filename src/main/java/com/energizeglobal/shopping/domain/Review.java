package com.energizeglobal.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
public class Review implements BaseEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "comment", length = 500, nullable = false)
    private String comment;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    @Column(name = "rate", nullable = false)
    private Integer rate;

    @ManyToOne
    @JsonIgnoreProperties(value = {"category"}, allowSetters = true)
    private Product product;

    @ManyToOne
    private User user;
}
