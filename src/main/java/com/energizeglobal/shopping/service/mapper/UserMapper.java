package com.energizeglobal.shopping.service.mapper;

import com.energizeglobal.shopping.domain.User;
import com.energizeglobal.shopping.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    UserDTO toDto(User s);

    User toEntity(UserDTO userDTO);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
