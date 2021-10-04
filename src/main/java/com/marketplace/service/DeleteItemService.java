package com.marketplace.service;

import com.marketplace.entity.*;
import com.marketplace.pojo.DeleteItemRequest;
import com.marketplace.repository.BasketRepository;
import com.marketplace.repository.ItemRepository;
import com.marketplace.repository.OrderRepository;
import com.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteItemService {
    private final ItemRepository itemRepository;
    private final BasketRepository basketRepository;
    private final OrderRepository orderRepository;
    private final MailSenderService mailSenderService;
    private final UserRepository userRepository;
    @PersistenceContext
    private final EntityManager entityManager;


    public void deleteItem(Map<Long, List<UserItem>> duplicatesInBaskets,
                           Map<Long, List<UserItem>> duplicatesInOrders,
                           Item itemBeforeDeleting,
                           DeleteItemRequest deleteRequest) {

         List<User> userList = new ArrayList<>();

        if (duplicatesInBaskets != null) {
            // for the current basket delete duplicates and save users for sending email
            for (Map.Entry<Long, List<UserItem>> entry : duplicatesInBaskets.entrySet()) {
                Basket basket = basketRepository.getBasketById(entry.getKey());

                List<UserItem> duplicatesUserItemInCurrentBasket = entry.getValue();
                List<UserItem> allUserItemInCurrentBasket = basket.getUserItem();

                deleteMatchedItems(duplicatesUserItemInCurrentBasket,
                        allUserItemInCurrentBasket);

                User user = userRepository.getById(basket.getUserId());
                userList.add(user);

            }
        }

        if (duplicatesInOrders != null) {
            // for the current order delete duplicates and save users for sending email
            for (Map.Entry<Long, List<UserItem>> entry : duplicatesInOrders.entrySet()) {
                Order order = orderRepository.getById(entry.getKey());

                List<UserItem> duplicatesUserItemInCurrentOrder = entry.getValue();
                List<UserItem> allUserItemInCurrentOrder = order.getUserItem();

                deleteMatchedItems(duplicatesUserItemInCurrentOrder,allUserItemInCurrentOrder);

                User user = userRepository.getById(order.getUserId());
                userList.add(user);
            }
        }

        itemRepository.deleteById(itemBeforeDeleting.getId());

        if (deleteRequest.isForceUpdate()) {
            String message = "your list of purchased items has been changed by administrator";
            mailSenderService.send(userList, "please check your chart", message);
        }
    }

    private void deleteMatchedItems
            (List < UserItem > matchedUserItemList,
             List<UserItem> userItemList){

        for (UserItem matchedItem : matchedUserItemList) {
            userItemList.remove(matchedItem);
            entityManager.remove(matchedItem);
        }
    }
}

