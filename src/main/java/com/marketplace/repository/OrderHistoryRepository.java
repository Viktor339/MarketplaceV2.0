package com.marketplace.repository;

import com.marketplace.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Long> {
    List<OrderHistory> findAll();
}
