package com.marketplace.service.basket;

import com.marketplace.entity.*;
import com.marketplace.service.exeption.BasketEmptyException;
import com.marketplace.service.exeption.ItemNotExistException;
import com.marketplace.service.exeption.ItemNotFoundException;
import com.marketplace.service.exeption.NotEnoughItemQuantityException;
import com.marketplace.controller.request.AddItemRequest;
import com.marketplace.controller.request.DeleteItemRequest;
import com.marketplace.controller.request.ItemMessage;
import com.marketplace.controller.request.ResponseMessage;
import com.marketplace.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.marketplace.entity.OrderHistoryStatus.Name.CREATED;
import static com.marketplace.entity.OrderStatus.Name.IN_PROCESSING;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {
    private final ItemRepository itemRepository;
    private final BasketRepository basketRepository;
    private final UserItemRepository userItemRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderHistoryStatusRepository orderHistoryStatusRepository;
    private final MailSenderService mailSenderService;
    @PersistenceContext
    private final EntityManager entityManager;

    public ResponseMessage addItem(AddItemRequest addRequest, User user) {

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
        return new ResponseMessage("Item added into basket");
    }


    public Basket buy(User user) {

        Basket userBasket = basketRepository.getBasketByUserId(user.getId());
        if (userBasket == null) {
            throw new BasketEmptyException("The basket is empty!");
        }
        List<UserItem> userItems = userBasket.getUserItem();
        basketRepository.deleteAllByUserId(user.getId());

        Order order = Order.builder()
                .orderStatus(orderStatusRepository.findByName(IN_PROCESSING))
                .userId(user.getId())
                .userItem(userItems)
                .build();

        OrderHistory orderHistory = OrderHistory.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .date(LocalDateTime.now())
                .orderHistoryStatus(orderHistoryStatusRepository.findByName(CREATED))
                .build();
        orderHistoryRepository.save(orderHistory);
        orderRepository.save(order);

        //get a list of all the user's items and send information about each item to the user's email
        List<User> userList = Collections.singletonList(user);
        List<Item> itemList = new ArrayList<>();
        for (UserItem userItem : userItems) {
            itemList.add(itemRepository.findItemById(userItem.getItemId()));
        }
        ItemMessage itemMessage = new ItemMessage(itemList);
        mailSenderService.send(userList, "please check your chart", itemMessage.toString());

        return userBasket;
    }

    public ResponseMessage deleteItem(DeleteItemRequest deleteRequest, User user) {

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
        return new ResponseMessage("Item deleted");
    }
}
