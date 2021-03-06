package com.marketplace.controller;

import com.marketplace.controller.request.ChangeItemRequest;
import com.marketplace.controller.request.CreateItemRequest;
import com.marketplace.controller.request.DeleteItemRequest;
import com.marketplace.service.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Item controller", description = "Allows admin to perform CRUD operations with items ")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Create item", description = "Allows admin to create")
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createItem(@RequestBody CreateItemRequest createRequest) {
        return ResponseEntity.ok(itemService.createItem(createRequest));
    }

    @Operation(summary = "Get items", description = "Allows admin and user get all item list")
    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @Operation(summary = "Change item", description = "Allows admin to change item")
    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeItem(@RequestBody ChangeItemRequest changeRequest) {
        return ResponseEntity.ok(itemService.changeItem(changeRequest));
    }

    @Operation(summary = "Delete item", description = "Allows admin to delete item")
    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteItem(@RequestBody DeleteItemRequest deleteRequest) {
        return ResponseEntity.ok(itemService.deleteItem(deleteRequest));
    }

}
