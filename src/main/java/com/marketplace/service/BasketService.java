package com.marketplace.service;

import com.marketplace.entity.Basket;
import com.marketplace.entity.Item;
import com.marketplace.entity.User;
import com.marketplace.entity.UserItem;
import com.marketplace.exeption.BasketEmptyException;
import com.marketplace.exeption.ItemNotFoundException;
import com.marketplace.exeption.NotEnoughItemQuantityException;
import com.marketplace.pojo.AddItemRequest;
import com.marketplace.pojo.DeleteItemRequest;
import com.marketplace.pojo.ItemsMessage;
import com.marketplace.pojo.ResponseMessage;
import com.marketplace.repository.BasketRepository;
import com.marketplace.repository.ItemRepository;
import com.marketplace.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {
    private final ItemRepository itemRepository;
    private final BasketRepository basketRepository;
    private final UserItemRepository userItemRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public ResponseEntity<?> addItem(AddItemRequest addRequest, User user) {

        Item addedItem = itemRepository.findItemById(addRequest.getId());
        if (addedItem != null) {
            if (addedItem.getAvailableQuantity() >= addRequest.getAddedQuantity()) {

                // reserve item
                addedItem.setAvailableQuantity(addedItem.getAvailableQuantity() - addRequest.getAddedQuantity());
                entityManager.persist(addedItem);
                entityManager.flush();

                Basket userBasket = basketRepository.getBasketByUserId(user.getId());
                if (userBasket == null) {
                    Basket basket = new Basket();
                    List<UserItem> itemList = new ArrayList<>();

                    UserItem userItem = new UserItem();
                    userItem.setQuantity(addRequest.getAddedQuantity());
                    userItem.setUserId(user.getId());
                    userItem.setItemId(addedItem.getId());

                    itemList.add(userItem);
                    basket.setUserItem(itemList);
                    basket.setUserId(user.getId());
                    basketRepository.save(basket);
                } else {
                    List<UserItem> itemsInTheBasket = userBasket.getUserItem();

                    //if added item in already in the basket -> change quantity
                    // else -> add new userItem
                    boolean isContains = false;
                    for (UserItem item : itemsInTheBasket) {
                        if (item.getItemId() == addRequest.getId()) {
                            item.setQuantity(item.getQuantity() + addRequest.getAddedQuantity());
                            isContains = true;
                            break;
                        }
                    }
                    if (!isContains) {
                        UserItem userItem = new UserItem();
                        userItem.setQuantity(addRequest.getAddedQuantity());
                        userItem.setUserId(user.getId());
                        userItem.setItemId(addedItem.getId());
                        itemsInTheBasket.add(userItem);
                    }
                    entityManager.persist(userBasket);
                    entityManager.flush();
                }
            } else {
                throw new ItemNotFoundException("Entered item doesn't found");
            }
        } else {
            throw new NotEnoughItemQuantityException("Required quantity of items exceeds available quantity exception");
        }
        return ResponseEntity.ok(new ResponseMessage("Item added into basket"));
    }


    public ResponseEntity<?> buyItem(User user) {

        Basket userBasket = basketRepository.getBasketByUserId(user.getId());
        if (userBasket == null) {
            throw new BasketEmptyException("The basket is empty!");
        }
        List<UserItem> userItemList = userBasket.getUserItem();
        // set into map inf about item
        Map<Item, Long> itemAndQuantityMap = new LinkedHashMap<>();
        userItemList
                .forEach(userItem -> itemAndQuantityMap
                        .put(itemRepository.getById(userItem.getItemId()), userItem.getQuantity()));

        ItemsMessage message = new ItemsMessage(itemAndQuantityMap);
        basketRepository.deleteAllByUserId(user.getId());
        return ResponseEntity.ok(new ResponseMessage("Email with information about purchased sended on your email" + message));
    }

    public ResponseEntity<?> deleteItem(DeleteItemRequest deleteRequest, User user) {

        Basket userBasket = basketRepository.getBasketByUserId(user.getId());
        if (userBasket == null) {
            throw new BasketEmptyException("The basket is empty!");
        }
        List<UserItem> itemsInTheBasket = userBasket.getUserItem();
        Item removedItem = itemRepository.findItemById(deleteRequest.getId());

        // looking removed item in the user basket
        for (UserItem item : itemsInTheBasket) {
            if (item.getItemId() == deleteRequest.getId()) {
                Long itemQuantityInDeletedItem = item.getQuantity();
                itemsInTheBasket.remove(item);
                userItemRepository.deleteAllByItemIdAndUserId(item.getItemId(), user.getId());

                //update the available quantity of items
                removedItem.setAvailableQuantity(removedItem.getAvailableQuantity() + itemQuantityInDeletedItem);
                entityManager.persist(removedItem);
                entityManager.persist(userBasket);
                entityManager.flush();
                break;
            } else {
                throw new ItemNotFoundException("Removed item doesn't found");
            }
        }
        return ResponseEntity.ok(new ResponseMessage("Item deteled"));
    }
}
