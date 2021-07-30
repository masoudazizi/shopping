package com.energizeglobal.shopping.service;

import com.energizeglobal.shopping.service.dto.BaseUserDTO;

public interface UserService {

    void registerUser(BaseUserDTO.ManagedUserDTO managedUserDTO, String password);

    void setBlockingState(Long userId, BaseUserDTO.BlockingDTO blockingDTO);

}
