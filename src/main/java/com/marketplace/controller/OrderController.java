package com.marketplace.controller;

import com.marketplace.controller.request.ChangeOrderRequest;
import com.marketplace.controller.request.CreateOrderRequest;
import com.marketplace.controller.request.DeleteOrderRequest;
import com.marketplace.service.order.OrderService;
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
@PreAuthorize("hasRole('ADMIN')")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create order", description = "Allows admin to create order")
    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest createRequest) {
        return ResponseEntity.ok(orderService.createOrder(createRequest));
    }

    @Operation(summary = "Change order", description = "Allows admin to change order")
    @PutMapping()
    public ResponseEntity<?> changeOrder(@RequestBody ChangeOrderRequest changeRequest) {
        return ResponseEntity.ok(orderService.changeOrder(changeRequest));
    }

    @Operation(summary = "Get orders list", description = "Allows admin to get orders list")
    @GetMapping()
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Delete order", description = "Allows admin to delete order")
    @DeleteMapping()
    public ResponseEntity<?> deleteOrder(@RequestBody DeleteOrderRequest deleteOrder) {
        return ResponseEntity.ok(orderService.deleteOrder(deleteOrder));
    }
}
