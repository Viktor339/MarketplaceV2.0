package com.marketplace.controller;

import com.marketplace.controller.request.RegistrationRequest;
import com.marketplace.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "User controller", description = "Allows to register admins and users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create user", description = "Allows to register admins and users")
    @PostMapping()
    public ResponseEntity<?> registrationUser(@RequestBody RegistrationRequest registrationRequest) {
        return userService.registrationUser(registrationRequest);
    }


}
