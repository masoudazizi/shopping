package com.energizeglobal.shopping.service.mapper;

import com.energizeglobal.shopping.domain.User;
import com.energizeglobal.shopping.service.dto.BaseUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<BaseUserDTO, User> {

    BaseUserDTO toDto(User s);

    User toEntity(BaseUserDTO baseUserDTO);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
