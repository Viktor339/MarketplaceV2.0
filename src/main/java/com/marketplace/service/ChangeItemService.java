package com.marketplace.service;

import com.marketplace.entity.*;
import com.marketplace.pojo.ChangeItemRequest;
import com.marketplace.repository.BasketRepository;
import com.marketplace.repository.ItemRepository;
import com.marketplace.repository.OrderRepository;
import com.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChangeItemService {
    private final ItemRepository itemRepository;
    private final BasketRepository basketRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;


    public void changeItem(Map<Long, List<UserItem>> duplicatesInBaskets,
                           Map<Long, List<UserItem>> duplicatesInOrders,
                           ChangeItemRequest changeRequest,Item changedItem) {

        String nameOfTheItemToBeChanged = changedItem.getName();
        String newName = changeRequest.getNewItemName();
        String newTags = changeRequest.getNewItemTags();
        String newDescription = changeRequest.getNewItemDescription();
        Long newQuantity = changeRequest.getQuantity();

        //if we do not change some criteria, we get them from the old element
        if (newName == null) {
            newName = changedItem.getName();
        }
        if (newTags == null) {
            newTags = changedItem.getTags();
        }
        if (newDescription == null) {
            newDescription = changedItem.getDescription();
        }
        if (newQuantity == null) {
            newQuantity = changedItem.getAvailableQuantity();
        }

        List<User> userList = new ArrayList<>();

        // for the current basket and order delete duplicates and save users for sending email
        if (duplicatesInBaskets != null) {
            for (Map.Entry<Long, List<UserItem>> entry : duplicatesInBaskets.entrySet()) {
                Basket basket = basketRepository.getBasketById(entry.getKey());

                User user = userRepository.getById(basket.getUserId());
                userList.add(user);
            }
        }
        if (duplicatesInOrders != null) {
            for (Map.Entry<Long, List<UserItem>> entry : duplicatesInOrders.entrySet()) {
                Order order = orderRepository.getById(entry.getKey());

                User user = userRepository.getById(order.getUserId());
                userList.add(user);
            }
        }

        itemRepository.changeItem(nameOfTheItemToBeChanged,
                newName,
                newDescription,
                newTags,
                newQuantity);

        if (changeRequest.isForceUpdate()) {
            String message = "your list of purchased items has been changed by administrator";
            mailSenderService.send(userList, "please check your chart", message);
        }
    }
}

