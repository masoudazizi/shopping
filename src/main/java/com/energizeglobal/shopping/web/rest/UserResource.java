package com.energizeglobal.shopping.web.rest;

import com.energizeglobal.shopping.config.Constants;
import com.energizeglobal.shopping.security.jwt.JWTFilter;
import com.energizeglobal.shopping.security.jwt.TokenProvider;
import com.energizeglobal.shopping.service.impl.UserServiceImpl;
import com.energizeglobal.shopping.service.dto.UserDTO;
import com.energizeglobal.shopping.service.dto.LoginDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final static Logger log = LoggerFactory.getLogger(UserResource.class);
    private final UserServiceImpl userServiceImpl;
    private final TokenProvider tokenProvider;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody UserDTO.ManagedUserDTO managedUserDTO) {
        log.info("request for register : {}", managedUserDTO);
        userServiceImpl.registerUser(managedUserDTO, managedUserDTO.getPassword());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserResource.JWTToken> authorize(@Valid @RequestBody LoginDTO loginDTO) {
        String jwt = tokenProvider.provideToken(loginDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new UserResource.JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/admin/block/{userId}/{blocked}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    public void blocked(@PathVariable(value = "userId", required = false) Long userId, @PathVariable(value = "blocked", required = false) Boolean blocked) {
        log.info("request for blocking or unblocking a user with id {} and blocked status {} :", userId, blocked);
        userServiceImpl.blockUser(userId, blocked);
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
