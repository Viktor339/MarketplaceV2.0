package com.marketplace.repository;

import com.marketplace.entity.OrderHistoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryStatusRepository extends JpaRepository<OrderHistoryStatus,Long> {
    OrderHistoryStatus findByName(OrderHistoryStatus.Name status);

}
