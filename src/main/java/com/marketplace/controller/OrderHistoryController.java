package com.marketplace.controller;

import com.marketplace.service.orderhistory.OrderHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order history controller", description = "Allows to get all the information about changes in orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders-history")
@PreAuthorize("hasRole('ADMIN')")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    @Operation(summary = "Get orders history ", description = "Allows admin to get orders history list")
    @GetMapping
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(orderHistoryService.getHistory());
    }
}
