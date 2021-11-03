package com.marketplace.controller;

import com.marketplace.entity.User;
import com.marketplace.controller.request.AddItemRequest;
import com.marketplace.controller.request.DeleteItemRequest;
import com.marketplace.repository.UserRepository;
import com.marketplace.service.basket.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Basket controller", description = "Allows user to add/buy/delete items from basket")
@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
@PreAuthorize("hasRole('USER')")
public class BasketController {

    private final BasketService basketService;
    private final UserRepository userRepository;


    @Operation(summary = "Add item", description = "Allows user to add item into basket")
    @PutMapping("/items")
    public ResponseEntity<?> addItem(@RequestBody AddItemRequest addRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findAllByUsername(userDetails.getUsername());
        return ResponseEntity.ok(basketService.addItem(addRequest, user));
    }

    @Operation(summary = "Buy item", description = "Allows user to buy all items it basket")
    @GetMapping("/items")
    public ResponseEntity<?> buy(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findAllByUsername(userDetails.getUsername());
        return ResponseEntity.ok(basketService.buy(user));
    }

    @Operation(summary = "Delete item", description = "Allows user to delete item from basket")
    @DeleteMapping("/items")
    public ResponseEntity<?> deleteItem(@RequestBody DeleteItemRequest deleteItemRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findAllByUsername(userDetails.getUsername());
        return ResponseEntity.ok(basketService.deleteItem(deleteItemRequest,user));
    }

}
