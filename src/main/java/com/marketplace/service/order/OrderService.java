package com.marketplace.service.order;

import com.marketplace.entity.*;
import com.marketplace.service.exeption.*;
import com.marketplace.controller.request.ChangeOrderRequest;
import com.marketplace.controller.request.CreateOrderRequest;
import com.marketplace.controller.request.DeleteOrderRequest;
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
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderHistoryStatusRepository orderHistoryStatusRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public Order createOrder(CreateOrderRequest createRequest) {

        //cant create order if user already has order
        // admin can only change orderStatus
        if (orderRepository.existsOrderByUserId(createRequest.getUserId())) {
            throw new UserOrderAlreadyExistException
                    ("Current user already has order. You can change only order status by this user");
        }
        Order order = new Order();
        List<UserItem> resultUserItemList = new ArrayList<>();

        //check for each itemId from request that item is exist and check available quantity of item
        for (UserItem requestUserItem : createRequest.getAddedItems()) {
            Item addedItem = itemRepository.findItemById(requestUserItem.getItemId());

            if (addedItem == null) {
                throw new ItemNotExistException("Entered item doesn't exist");
            }
            if (addedItem.getAvailableQuantity() < requestUserItem.getQuantity()) {
                throw new NotEnoughItemQuantityException("Required quantity of items exceeds available quantity");
            }

            UserItem newUserItem = UserItem.builder()
                    .userId(createRequest.getUserId())
                    .itemId(requestUserItem.getItemId())
                    .quantity(requestUserItem.getQuantity())
                    .build();
                      userItemRepository.save(newUserItem);

            addedItem.setAvailableQuantity(addedItem.getAvailableQuantity() - requestUserItem.getQuantity());
            resultUserItemList.add(newUserItem);
        }

        order.setOrderStatus(statusService.returnStatus(createRequest.getStatus()));
        order.setUserItem(resultUserItemList);
        order.setUserId(createRequest.getUserId());

        OrderHistory orderHistory = OrderHistory.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .date(LocalDateTime.now())
                .orderHistoryStatus(orderHistoryStatusRepository.findByName(OrderHistoryStatus.Name.CREATED))
                .build();
        orderHistoryRepository.save(orderHistory);


        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    public ResponseMessage changeOrder(ChangeOrderRequest changeRequest) {
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

        OrderHistory orderHistory = OrderHistory.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .date(LocalDateTime.now())
                .orderHistoryStatus(orderHistoryStatusRepository.findByName(OrderHistoryStatus.Name.CHANGED))
                .build();
        orderHistoryRepository.save(orderHistory);

        return new ResponseMessage("Order changed");
    }

    public ResponseMessage deleteOrder(DeleteOrderRequest deleteRequest) {
        Order order = orderRepository.findOrderById(deleteRequest.getOrderId());
        if (order == null) {
            throw new UserOrderNotExistException("No orders found for this order id");
        }

        //check each userItem and increase the available quantity
        List<UserItem> userItems = order.getUserItem();
        for (UserItem deletedItem : userItems) {
            Item item = itemRepository.findItemById(deletedItem.getItemId());
            item.setAvailableQuantity(item.getAvailableQuantity() + deletedItem.getQuantity());
            entityManager.persist(item);
        }

        //after deletion, the status changes to "DELETED"
        order.setOrderStatus(statusService.returnStatus(OrderStatus.Name.DELETED.name()));

        OrderHistory orderHistory = OrderHistory.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .date(LocalDateTime.now())
                .orderHistoryStatus(orderHistoryStatusRepository.findByName(OrderHistoryStatus.Name.DELETED))
                .build();
        orderHistoryRepository.save(orderHistory);
        return new ResponseMessage("Order deleted");
    }

}

