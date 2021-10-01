package com.marketplace.service;

import com.marketplace.entity.OrderHistory;
import com.marketplace.repository.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {
    private final OrderHistoryRepository orderHistoryRepository;

    public ResponseEntity<?> getHistory() {
        List<OrderHistory> orderHistory = orderHistoryRepository.findAll();
        return ResponseEntity.ok(orderHistory);
    }
}
