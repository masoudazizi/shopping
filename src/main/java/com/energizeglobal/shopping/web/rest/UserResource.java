package com.energizeglobal.shopping.web.rest;

import com.energizeglobal.shopping.config.Constants;
import com.energizeglobal.shopping.security.jwt.JWTFilter;
import com.energizeglobal.shopping.security.jwt.TokenProvider;
import com.energizeglobal.shopping.service.UserService;
import com.energizeglobal.shopping.service.dto.BaseUserDTO;
import com.energizeglobal.shopping.service.impl.UserServiceImpl;
import com.energizeglobal.shopping.service.dto.LoginDTO;
import com.energizeglobal.shopping.web.rest.exceptions.BadRequestException;
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
import javax.validation.constraints.NotNull;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final static Logger log = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody BaseUserDTO.ManagedBaseUserDTO managedUserDTO) {
        log.info("request for register : {}", managedUserDTO);
        userService.registerUser(managedUserDTO, managedUserDTO.getPassword());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserResource.JWTToken> authorize(@Valid @RequestBody LoginDTO loginDTO) {
        String jwt = tokenProvider.provideToken(loginDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new UserResource.JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN + "\")")
    @PatchMapping(value = "/admin/set-blocking/{userId}", consumes = "application/merge-patch+json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void blockOrUnblock(
            @PathVariable(value = "userId", required = false) final Long userId,
            @NotNull @RequestBody BaseUserDTO.BlockingDTO blockingDTO){
        log.debug("REST request to partial update user partially : {}, {}", userId, blockingDTO);
        userService.setBlockingState(userId,blockingDTO);
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
