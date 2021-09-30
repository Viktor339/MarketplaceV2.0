package com.marketplace.controller;

import com.marketplace.pojo.ChangeItemRequest;
import com.marketplace.pojo.CreateItemRequest;
import com.marketplace.pojo.DeleteItemRequest;
import com.marketplace.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createItem(@RequestBody CreateItemRequest createRequest) {
        return itemService.createItem(createRequest);
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllItems() {
        return itemService.getAllItems();
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeItem(@RequestBody ChangeItemRequest changeRequest) {
        return itemService.changeItem(changeRequest);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteItem(@RequestBody DeleteItemRequest deleteRequest) {
        return itemService.deleteItem(deleteRequest);
    }

}
