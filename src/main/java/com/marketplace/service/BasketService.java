package com.marketplace.service;

import com.marketplace.entity.*;
import com.marketplace.exeption.BasketEmptyException;
import com.marketplace.exeption.ItemNotExistException;
import com.marketplace.exeption.ItemNotFoundException;
import com.marketplace.exeption.NotEnoughItemQuantityException;
import com.marketplace.pojo.AddItemRequest;
import com.marketplace.pojo.DeleteItemRequest;
import com.marketplace.pojo.ResponseMessage;
import com.marketplace.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static com.marketplace.entity.Status.Name.ORDER_IS_BEING_PROCESSED;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {
    private final ItemRepository itemRepository;
    private final BasketRepository basketRepository;
    private final UserItemRepository userItemRepository;
    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public ResponseEntity<?> addItem(AddItemRequest addRequest, User user) {

        Item addedItem = itemRepository.findItemById(addRequest.getId());
        if (addedItem == null) {
            throw new ItemNotExistException("Entered item doesn't exist");
        }
        if (addedItem.getAvailableQuantity() < addRequest.getAddedQuantity()) {
            throw new NotEnoughItemQuantityException("Required quantity of items exceeds available quantity exception");
        }

        // reserve item
        addedItem.setAvailableQuantity(addedItem.getAvailableQuantity() - addRequest.getAddedQuantity());
        entityManager.persist(addedItem);
        entityManager.flush();

        Basket userBasket = basketRepository.getBasketByUserId(user.getId());
        if (userBasket == null) {
            Basket basket = new Basket();
            List<UserItem> itemList = new ArrayList<>();

            UserItem userItem = UserItem.builder()
                    .userId(user.getId())
                    .quantity(addRequest.getAddedQuantity())
                    .itemId(addedItem.getId())
                    .build();

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

                UserItem userItem = UserItem.builder()
                        .userId(user.getId())
                        .quantity(addRequest.getAddedQuantity())
                        .itemId(addedItem.getId())
                        .build();
                itemsInTheBasket.add(userItem);
            }
            entityManager.persist(userBasket);
            entityManager.flush();
        }
        return ResponseEntity.ok(new ResponseMessage("Item added into basket"));
    }


    public ResponseEntity<?> buy(User user) {

        Basket userBasket = basketRepository.getBasketByUserId(user.getId());
        if (userBasket == null) {
            throw new BasketEmptyException("The basket is empty!");
        }
        List<UserItem> userItems = userBasket.getUserItem();
        basketRepository.deleteAllByUserId(user.getId());

        Order order = Order.builder()
                .status(statusRepository.findByName(ORDER_IS_BEING_PROCESSED))
                .userId(user.getId())
                .items(userItems)
                .build();

        orderRepository.save(order);
        return ResponseEntity.ok(userBasket);
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
        return ResponseEntity.ok(new ResponseMessage("Item deleted"));
    }
}
