package com.marketplace.controller;

import com.marketplace.pojo.ChangeItemRequest;
import com.marketplace.pojo.CreateItemRequest;
import com.marketplace.pojo.DeleteItemRequest;
import com.marketplace.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Item controller", description = "Allows to perform CRUD operations with items ")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Create item", description = "Allows admin to create")
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createItem(@RequestBody CreateItemRequest createRequest) {
        return itemService.createItem(createRequest);
    }

    @Operation(summary = "Get items", description = "Allows admin and user get all item list")
    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllItems() {
        return itemService.getAllItems();
    }

    @Operation(summary = "Change item", description = "Allows admin to change item")
    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeItem(@RequestBody ChangeItemRequest changeRequest) {
        return itemService.changeItem(changeRequest);
    }

    @Operation(summary = "Delete item", description = "Allows admin to delete item")
    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteItem(@RequestBody DeleteItemRequest deleteRequest) {
        return itemService.deleteItem(deleteRequest);
    }

}
