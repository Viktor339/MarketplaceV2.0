package com.marketplace.service;

import com.marketplace.entity.Item;
import com.marketplace.entity.Order;
import com.marketplace.entity.UserItem;
import com.marketplace.exeption.*;
import com.marketplace.pojo.ChangeOrderRequest;
import com.marketplace.pojo.CreateOrderRequest;
import com.marketplace.pojo.DeleteOrderRequest;
import com.marketplace.repository.ItemRepository;
import com.marketplace.repository.OrderRepository;
import com.marketplace.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final StatusService statusService;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;
    private final AddingItemOrderService addingItemOrderService;
    private final RemovingItemOrderService removingItemOrderService;
    @PersistenceContext
    private final EntityManager entityManager;

    public ResponseEntity<?> createOrder(CreateOrderRequest createRequest) {

        //cant create order if user already has order
        // admin can only change orderStatus
        if (orderRepository.existsOrderByUserId(createRequest.getUserId())) {
            throw new UserOrderAlreadyExistException
                    ("Current user already has order. You can change only order status by this user");
        }
        Order order = new Order();
        List<UserItem> resultUserItemList = new ArrayList<>();

        //check for each itemId from request that item is exist and check available quantity of item
        for (UserItem userItem : createRequest.getAddedItems()) {
            Item addedItem = itemRepository.findItemById(userItem.getItemId());

            if (addedItem == null) {
                throw new ItemNotExistException("Entered item doesn't exist");
            }
            if (addedItem.getAvailableQuantity() < userItem.getQuantity()) {
                throw new NotEnoughItemQuantityException("Required quantity of items exceeds available quantity");
            }

            UserItem newUserItem = new UserItem();
            userItem.setUserId(createRequest.getUserId());
            userItem.setItemId(userItem.getItemId());
            userItem.setQuantity(userItem.getQuantity());
            userItemRepository.save(newUserItem);

            addedItem.setAvailableQuantity(addedItem.getAvailableQuantity() - userItem.getQuantity());
            resultUserItemList.add(userItem);
            entityManager.persist(addedItem);

        }
        order.setStatus(statusService.returnStatus(createRequest.getStatus()));
        order.setItems(resultUserItemList);
        order.setUserId(createRequest.getUserId());

        entityManager.persist(order);

        return ResponseEntity.ok(orderRepository.save(order));
    }

    public ResponseEntity<?> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return ResponseEntity.ok(orderList);
    }

    public ResponseEntity<?> changeOrder(ChangeOrderRequest changeRequest) {
        Order order = orderRepository.findByUserId(changeRequest.getUserId());
        if (order == null) {
            throw new UserOrderNotFoundException("No orders found for this user");
        }

        if (changeRequest.getAddedItems() != null) {
            addingItemOrderService.addItem(changeRequest, order);
        }
        if (changeRequest.getRemovedItems() != null) {
            removingItemOrderService.removeItem(changeRequest, order);
        }
        return ResponseEntity.ok("Order changed");
    }

    public ResponseEntity<?> deleteOrder(DeleteOrderRequest deleteRequest) {
        Order order = orderRepository.findOrderById(deleteRequest.getOrderId());
        if (order == null) {
            throw new UserOrderNotExistException("No orders found for this order id");
        }

        //check each userItem and increase the available quantity
        List<UserItem> userItems = order.getItems();
        for (UserItem deletedItem : userItems) {
            Item item = itemRepository.findItemById(deletedItem.getItemId());
            item.setAvailableQuantity(item.getAvailableQuantity() + deletedItem.getQuantity());
            entityManager.persist(item);
        }

        orderRepository.delete(order);
        return ResponseEntity.ok("order deleted");
    }

}

