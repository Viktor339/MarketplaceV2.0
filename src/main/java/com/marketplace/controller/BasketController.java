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
public class BasketController {

    private final BasketService basketService;
    private final UserRepository userRepository;


    @Operation(summary = "Add item", description = "Allows user to add item into basket")
    @PutMapping("/items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addItem(@RequestBody AddItemRequest addRequest, @AuthenticationPrincipal UserDetails user) {
        User loggedInUserFromDB = userRepository.findAllByUsername(user.getUsername());
        return basketService.addItem(addRequest, loggedInUserFromDB);
    }

    @Operation(summary = "Buy item", description = "Allows user to buy all items it basket")
    @GetMapping("/items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> buy(@AuthenticationPrincipal UserDetails user) {
        User loggedInUserFromDB = userRepository.findAllByUsername(user.getUsername());
        return basketService.buy(loggedInUserFromDB);
    }

    @Operation(summary = "Delete item", description = "Allows user to delete item from basket")
    @DeleteMapping("/items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteItem(@RequestBody DeleteItemRequest deleteItemRequest, @AuthenticationPrincipal UserDetails user) {
        User loggedInUserFromDB = userRepository.findAllByUsername(user.getUsername());
        return basketService.deleteItem(deleteItemRequest,loggedInUserFromDB);
    }

}
