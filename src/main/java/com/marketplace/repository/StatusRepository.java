package com.marketplace.repository;


import com.marketplace.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByName(Status.Name status);
}
