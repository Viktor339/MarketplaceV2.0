package com.marketplace.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@JsonIgnoreProperties(value = {"accountNonExpired", "enabled", "authorities", "accountNonLocked", "credentialsNonExpired", "accountNonExpired"})
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    User findAllByUsername(String username);
}
