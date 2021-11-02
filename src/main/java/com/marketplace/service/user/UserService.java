package com.marketplace.service.user;

import com.marketplace.entity.User;
import com.marketplace.controller.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RegistrationUserService registrationUserService;


    public ResponseEntity<?> registrationUser(RegistrationRequest registrationRequest) {

        User user = User.builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(roleService.getRole(registrationRequest))
                .build();


        return ResponseEntity.ok(registrationUserService.save(user));
    }


}
