package com.marketplace.repository;

import com.marketplace.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Boolean existsOrderByUserId(Long id);

    List<Order> findAll();

    Order findByUserId(Long id);

    Order findOrderById(Long id);


}
