package com.energizeglobal.shopping.service.impl.sepecification;

import com.energizeglobal.shopping.domain.Product;
import com.energizeglobal.shopping.service.dto.ProductSearchDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class ProductSpecifications {

    private static String TITLE = "title";
    private static String PRICE = "price";
    private static String ID = "id";

    public static Specification<Product> createSpecification(ProductSearchDTO filter, List<Integer> productIds) {
        Specification<Product> specification = Specification.where(null);

        if (filter.getToRate() != null & filter.getFromRate() != null){
            specification = specification.and((Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> root.get(ID).in(productIds));
        }

        if (filter.getTitle() != null) {
            specification = specification.and((Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get(TITLE), filter.getTitle()));
        }
        if (filter.getFromPrice() != null) {
            specification = specification.and((Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                    -> criteriaBuilder.greaterThanOrEqualTo(root.get(PRICE), filter.getFromPrice()));
        }
        if (filter.getToPrice() != null) {
            specification = specification.and((Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                    -> criteriaBuilder.lessThanOrEqualTo(root.get(PRICE), filter.getToPrice()));
        }
        return specification;
    }
}
