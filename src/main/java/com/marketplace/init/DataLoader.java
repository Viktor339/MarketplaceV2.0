package com.marketplace.init;

import com.marketplace.entity.Role;
import com.marketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static com.marketplace.entity.Role.Name.ROLE_ADMIN;
import static com.marketplace.entity.Role.Name.ROLE_USER;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public void run(ApplicationArguments args) {
        roleRepository.save(new Role(ROLE_ADMIN));
        roleRepository.save(new Role(ROLE_USER));

    }
}
