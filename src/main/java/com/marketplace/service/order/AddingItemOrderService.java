package com.marketplace.service.order;

import com.marketplace.entity.Item;
import com.marketplace.entity.Order;
import com.marketplace.entity.UserItem;
import com.marketplace.service.exeption.ItemNotExistException;
import com.marketplace.service.exeption.NotEnoughItemQuantityException;
import com.marketplace.controller.request.ChangeOrderRequest;
import com.marketplace.repository.ItemRepository;
import com.marketplace.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
@Transactional
public class AddingItemOrderService {

    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public void addItem(ChangeOrderRequest changeRequest, Order order) {

        for (UserItem userItem : changeRequest.getAddedItems()) {

            Item addedItem = itemRepository.findItemById(userItem.getItemId());
            if (addedItem == null) {
                throw new ItemNotExistException("Entered item doesn't exist");
            }

            if (addedItem.getAvailableQuantity() < userItem.getQuantity()) {
                throw new NotEnoughItemQuantityException("Required quantity of items exceeds available quantity");
            }

            boolean isContains = false;

            //  if userItem exists in basket -> change quantity
            //else -> create new userItem
            for (UserItem itemFromUserOrder : order.getUserItem()) {

                if (itemFromUserOrder.equals(userItem)) {
                    itemFromUserOrder.setUserId(changeRequest.getUserId());
                    itemFromUserOrder.setQuantity(userItem.getQuantity());

                    isContains = true;
                    break;
                }
            }
            if (!isContains) {
                UserItem newUserItem = UserItem.builder()
                        .userId(changeRequest.getUserId())
                        .itemId(userItem.getItemId())
                        .quantity(userItem.getQuantity())
                        .build();

                userItemRepository.save(newUserItem);
                order.getUserItem().add(newUserItem);
            }

            // reserve item
            addedItem.setAvailableQuantity(addedItem.getAvailableQuantity() - userItem.getQuantity());
            entityManager.persist(addedItem);
        }
    }
}
