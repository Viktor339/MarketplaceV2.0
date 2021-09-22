package com.marketplace.service;

import com.marketplace.entity.User;
import com.marketplace.pojo.RegistrationRequest;
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

        User user = new User.Builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(roleService.getRole(registrationRequest))
                .build();


        return ResponseEntity.ok(registrationUserService.save(user));
    }


}