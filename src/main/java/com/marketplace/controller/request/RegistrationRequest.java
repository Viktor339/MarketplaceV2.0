package com.marketplace.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    @Schema(description = "username", example = "user")
    private String username;
    @Schema(description = "email", example = "user@gmail.com")
    private String email;
    @Schema(description = "role", example = "user")
    private String role;
    @Schema(description = "password", example = "user")
    private String password;

}
