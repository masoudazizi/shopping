package com.energizeglobal.shopping.service.dto;


import com.energizeglobal.shopping.domain.Authority;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Accessors(chain = true)
public class UserDTO {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private boolean activated = true;

    private Set<Authority> authorities;

    @Data
    @Accessors(chain = true)
    public static class ManagedUserDTO extends UserDTO {
        public static final int PASSWORD_MIN_LENGTH = 4;
        public static final int PASSWORD_MAX_LENGTH = 100;
        @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
        private String password;
    }
}
