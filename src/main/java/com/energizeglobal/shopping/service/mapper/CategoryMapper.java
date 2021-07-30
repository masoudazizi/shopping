package com.energizeglobal.shopping.service.mapper;

import com.energizeglobal.shopping.domain.Category;
import com.energizeglobal.shopping.service.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "parentId", target = "parent")
    Category toEntity(CategoryDTO e);

    @Mapping(source = "parent.id", target = "parentId")
    CategoryDTO toDto(Category s);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
