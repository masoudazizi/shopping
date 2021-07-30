package com.energizeglobal.shopping.service;

import com.energizeglobal.shopping.service.dto.UserDTO;

public interface UserService {

    void registerUser(UserDTO userDTO, String password);

    void blockUser(Long userId, Boolean blocked);

}
