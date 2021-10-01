package com.marketplace.service;

import com.marketplace.entity.OrderStatus;
import com.marketplace.exeption.StatusNotFoundException;
import com.marketplace.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatus returnStatus(String status) {

        OrderStatus orderWithOrderStatus = orderStatusRepository.findByName(OrderStatus.Name.IN_PROCESSING);
        switch (status) {
            case "IN_PROCESSING":
                orderWithOrderStatus = orderStatusRepository.findByName(OrderStatus.Name.IN_PROCESSING);
                break;
            case "SENT":
                orderWithOrderStatus = orderStatusRepository.findByName(OrderStatus.Name.SENT);
                break;
            case "DELIVERED":
                orderWithOrderStatus = orderStatusRepository.findByName(OrderStatus.Name.DELIVERED);
                break;
            case "DELETED":
                orderWithOrderStatus = orderStatusRepository.findByName(OrderStatus.Name.DELETED);
                break;
        }
        if (!status.equals(orderWithOrderStatus.getName().name())) {
            throw new StatusNotFoundException("Entered status not found");
        }

        return orderWithOrderStatus;
    }
}
