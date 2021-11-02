package com.marketplace.service.user;

import com.marketplace.entity.User;
import com.marketplace.service.exeption.EmailOrUsernameAlreadyExistsException;
import com.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationUserService {

    private final UserRepository userRepository;

    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new EmailOrUsernameAlreadyExistsException(
                    "User with this name or email already exists");
        }
    }
}
