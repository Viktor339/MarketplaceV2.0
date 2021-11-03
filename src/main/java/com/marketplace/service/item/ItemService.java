package com.marketplace.service.item;

import com.marketplace.entity.Item;
import com.marketplace.entity.UserItem;
import com.marketplace.service.exeption.ItemAlreadyExistException;
import com.marketplace.service.exeption.ItemNotExistException;
import com.marketplace.controller.request.ChangeItemRequest;
import com.marketplace.controller.request.CreateItemRequest;
import com.marketplace.controller.request.DeleteItemRequest;
import com.marketplace.controller.request.ResponseMessage;
import com.marketplace.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final DeleteItemService deleteItemService;
    private final SearchItemInBasketsService searchItemInBasketsService;
    private final SearchItemInOrdersService searchItemInOrdersService;
    private final ChangeItemService changeItemService;


    public ResponseMessage createItem(@RequestBody CreateItemRequest createRequest) {
        //create item with unique name
        if (itemRepository.existsByName(createRequest.getName())) {
            throw new ItemAlreadyExistException("Product with this name has already been created");
        } else
            itemRepository.createNewItem(createRequest.getName(), createRequest.getDescription(), createRequest.getTags(), createRequest.getAvailableQuantity());
        return new ResponseMessage("Added a new item");
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public ResponseMessage changeItem(ChangeItemRequest changeRequest) {
        //The item we want to change must not be in the cart.
        //throw bad request if the item is in the user's cart
        Item changedItem = itemRepository.findItemById(changeRequest.getItemId());

        if (changedItem == null) {
            throw new ItemNotExistException("The item to be removed does not exist");
        }

        Map<Long, List<UserItem>> duplicatesInBaskets = searchItemInBasketsService.searchItem(changedItem,changeRequest.isForceUpdate());
        Map<Long, List<UserItem>> duplicatesInOrders = searchItemInOrdersService.searchItem(changedItem,changeRequest.isForceUpdate());

        // if duplicatesInBaskets or duplicatesInOrders != null then we must change item anyway
        if (duplicatesInBaskets!=null||duplicatesInOrders!=null) {

            changeItemService.changeItem(duplicatesInBaskets,
                    duplicatesInOrders,
                    changeRequest,changedItem
            );
        }

        return new ResponseMessage("Item changed");
    }

    public ResponseMessage deleteItem(DeleteItemRequest deleteRequest) {

        Item removedItem = itemRepository.findItemById(deleteRequest.getId());

        if (removedItem == null) {
            throw new ItemNotExistException("The item to be removed does not exist");
        }

        // if user's basket or order contains removedItem we must to throw exception and do not delete item
        Map<Long, List<UserItem>> duplicatesInBaskets = searchItemInBasketsService.searchItem(removedItem,deleteRequest.isForceUpdate());
        Map<Long, List<UserItem>> duplicatesInOrders = searchItemInOrdersService.searchItem(removedItem,deleteRequest.isForceUpdate());

        // if duplicatesInBaskets or duplicatesInOrders != null then we must delete item anyway
        if (duplicatesInBaskets!=null||duplicatesInOrders!=null) {
            deleteItemService.deleteItem(duplicatesInBaskets,duplicatesInOrders,removedItem,deleteRequest);
        }
        return new ResponseMessage("Item deleted");
    }
}
