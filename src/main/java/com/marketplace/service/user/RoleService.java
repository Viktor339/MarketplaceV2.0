package com.marketplace.service.user;

import com.marketplace.entity.Role;
import com.marketplace.controller.request.RegistrationRequest;
import com.marketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(RegistrationRequest registrationRequest) {
        if ("admin".equals(registrationRequest.getRole())) {
            Role role=roleRepository.findByName(Role.Name.ROLE_ADMIN);
            return roleRepository.findByName(Role.Name.ROLE_ADMIN);
        } else return roleRepository.findByName(Role.Name.ROLE_USER);
    }
}
