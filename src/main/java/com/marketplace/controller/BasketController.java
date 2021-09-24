package com.marketplace.controller;

import com.marketplace.entity.User;
import com.marketplace.pojo.AddItemRequest;
import com.marketplace.pojo.DeleteItemRequest;
import com.marketplace.repository.UserRepository;
import com.marketplace.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;
    private final UserRepository userRepository;


    @PutMapping("/items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addItem(@RequestBody AddItemRequest addRequest, @AuthenticationPrincipal UserDetails user) {
        User loggedInUserFromDB = userRepository.findAllByUsername(user.getUsername());
        return basketService.addItem(addRequest, loggedInUserFromDB);
    }

    @GetMapping("/items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> buyItem(@AuthenticationPrincipal UserDetails user) {
        User loggedInUserFromDB = userRepository.findAllByUsername(user.getUsername());
        return basketService.buyItem(loggedInUserFromDB);
    }

    @DeleteMapping("/items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteItem(@RequestBody DeleteItemRequest deleteItemRequest, @AuthenticationPrincipal UserDetails user) {
        User loggedInUserFromDB = userRepository.findAllByUsername(user.getUsername());
        return basketService.deleteItem(deleteItemRequest,loggedInUserFromDB);
    }

}
