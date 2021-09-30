package com.marketplace.service;

import com.marketplace.entity.Item;
import com.marketplace.exeption.ItemAlreadyExistException;
import com.marketplace.exeption.ItemNotExistException;
import com.marketplace.pojo.ChangeItemRequest;
import com.marketplace.pojo.CreateItemRequest;
import com.marketplace.pojo.DeleteItemRequest;
import com.marketplace.pojo.ResponseMessage;
import com.marketplace.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final SearchItemService searchItemService;

    public ResponseEntity<?> createItem(@RequestBody CreateItemRequest createRequest) {
        //create item with unique name
        if (itemRepository.existsByName(createRequest.getName())) {
            throw new ItemAlreadyExistException("Product with this name has already been created");
        } else
            itemRepository.createNewItem(createRequest.getName(), createRequest.getDescription(), createRequest.getTags(), createRequest.getAvailableQuantity());
        return ResponseEntity.ok(new ResponseMessage("Added a new item"));
    }

    public ResponseEntity<?> getAllItems() {
        List<Item> itemList = itemRepository.findAll();
        return ResponseEntity.ok(itemList);
    }

    public ResponseEntity<?> changeItem(ChangeItemRequest changeRequest) {
        //The item we want to change must not be in the cart.
        //throw bad request if the item is in the user's cart
        Item itemBeforeChange = itemRepository.findItemById(changeRequest.getItemId());

        if (!searchItemService.searchItem(itemBeforeChange)) {

            String nameOfTheItemToBeChanged = itemBeforeChange.getName();
            String newName = changeRequest.getNewItemName();
            String newTags = changeRequest.getNewItemTags();
            String newDescription = changeRequest.getNewItemDescription();
            Long newQuantity = changeRequest.getQuantity();

            //if we do not change some criteria, we get them from the old element
            if (newName == null) {
                newName = itemBeforeChange.getName();
            }

            if (newTags == null) {
                newTags = itemBeforeChange.getTags();
            }
            if (newDescription == null) {
                newDescription = itemBeforeChange.getDescription();
            }
            if (newQuantity == null) {
                newQuantity = itemBeforeChange.getAvailableQuantity();
            }

            itemRepository.changeItem(nameOfTheItemToBeChanged,
                    newName,
                    newDescription,
                    newTags,
                    newQuantity);
        }

        return ResponseEntity.ok(new ResponseMessage("Item changed"));
    }

    public ResponseEntity<?> deleteItem(DeleteItemRequest deleteRequest) {

        Item itemBeforeDeleting = itemRepository.findItemById(deleteRequest.getId());

        if (itemBeforeDeleting == null) {
            throw new ItemNotExistException("The item to be removed does not exist");
        }

        if (!searchItemService.searchItem(itemBeforeDeleting)) {
            itemRepository.deleteById(deleteRequest.getId());
        }
        return ResponseEntity.ok(new ResponseMessage("Item deleted"));
    }
}
