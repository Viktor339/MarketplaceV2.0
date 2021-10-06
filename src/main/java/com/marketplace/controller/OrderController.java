package com.marketplace.controller;

import com.marketplace.pojo.ChangeOrderRequest;
import com.marketplace.pojo.CreateOrderRequest;
import com.marketplace.pojo.DeleteOrderRequest;
import com.marketplace.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order controller", description = "Allows admin to perform CRUD operations with orders ")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create order", description = "Allows admin to create order")
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest createRequest) {
        return orderService.createOrder(createRequest);
    }

    @Operation(summary = "Change order", description = "Allows admin to change order")
    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeOrder(@RequestBody ChangeOrderRequest changeRequest) {
        return orderService.changeOrder(changeRequest);
    }

    @Operation(summary = "Get orders list", description = "Allows admin to get orders list")
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Delete order", description = "Allows admin to delete order")
    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteOrder(@RequestBody DeleteOrderRequest deleteOrder) {
        return orderService.deleteOrder(deleteOrder);
    }
}
