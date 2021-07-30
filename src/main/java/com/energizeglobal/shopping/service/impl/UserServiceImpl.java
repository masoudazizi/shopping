package com.energizeglobal.shopping.service.impl;

import com.energizeglobal.shopping.config.Constants;
import com.energizeglobal.shopping.domain.Authority;
import com.energizeglobal.shopping.domain.User;
import com.energizeglobal.shopping.repository.AuthorityRepository;
import com.energizeglobal.shopping.repository.UserRepository;
import com.energizeglobal.shopping.service.UserService;
import com.energizeglobal.shopping.service.dto.BaseUserDTO;
import com.energizeglobal.shopping.service.mapper.UserMapper;
import com.energizeglobal.shopping.web.rest.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;

    private void userExistValidation(BaseUserDTO.ManagedUserDTO managedUserDTO) {
        userRepository
                .findOneByLogin(managedUserDTO.getLogin().toLowerCase())
                .ifPresent(
                        existingUser -> {
                            throw new BadRequestException("error.userName.already.used");
                        }
                );
        userRepository
                .findOneByEmailIgnoreCase(managedUserDTO.getEmail())
                .ifPresent(
                        existingUser -> {
                            throw new BadRequestException("error.email.already.used");
                        }
                );
    }


    public void registerUser(BaseUserDTO.ManagedUserDTO managedUserDTO, String password) {
        userExistValidation(managedUserDTO);
        User user = userMapper.toEntity(managedUserDTO);
        user.setLogin(managedUserDTO.getLogin().toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        if (managedUserDTO.getEmail() != null) {
            user.setEmail(managedUserDTO.getEmail().toLowerCase());
        }
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(Constants.USER).ifPresent(authorities::add);
        user.setAuthorities(authorities);
        User newUser = userRepository.save(user);
        log.debug("Created Information for User: {}", newUser);
    }


    public void setBlockingState(Long userId, BaseUserDTO.BlockingDTO blockingDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new BadRequestException("error.user.not.exist");
        }
        if (userOptional.get().isActivated() == Boolean.TRUE && userOptional.get().isActivated() == blockingDTO.isActivated()) {
            throw new BadRequestException("user.is.already.unblocked");
        }
        if (userOptional.get().isActivated() == Boolean.FALSE && userOptional.get().isActivated() == blockingDTO.isActivated()) {
            throw new BadRequestException("user.is.already.blocked");
        }
        userOptional.ifPresent(userPresent -> {
            userPresent.setActivated(blockingDTO.isActivated());
            userRepository.save(userOptional.get());
        });

    }


//    public void blockUser(Long userId, Boolean blocked) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (!userOptional.isPresent()) {
//            throw new BadRequestException("error.user.not.exist");
//        }
//        if (userOptional.get().isActivated() == Boolean.TRUE && userOptional.get().isActivated() == blocked) {
//            throw new BadRequestException("user.is.already.unblocked");
//        }
//        if (userOptional.get().isActivated() == Boolean.FALSE && userOptional.get().isActivated() == blocked) {
//            throw new BadRequestException("user.is.already.blocked");
//        }
//        userOptional.ifPresent(userPresent -> {
//            userPresent.setActivated(blocked);
//            userRepository.save(userOptional.get());
//        });
//
//    }

}
