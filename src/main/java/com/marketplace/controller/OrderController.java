package com.marketplace.controller;

import com.marketplace.pojo.ChangeOrderRequest;
import com.marketplace.pojo.CreateOrderRequest;
import com.marketplace.pojo.DeleteOrderRequest;
import com.marketplace.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest createRequest) {
        return orderService.createOrder(createRequest);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeOrder(@RequestBody ChangeOrderRequest changeRequest) {
        return orderService.changeOrder(changeRequest);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders() {
        return orderService.getAllOrders();
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteOrder(@RequestBody DeleteOrderRequest deleteOrder) {
        return orderService.deleteOrder(deleteOrder);
    }
}
