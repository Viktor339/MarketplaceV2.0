package com.marketplace.service;

import com.marketplace.entity.Item;
import com.marketplace.entity.Order;
import com.marketplace.entity.UserItem;
import com.marketplace.exeption.ItemNotFoundException;
import com.marketplace.pojo.ChangeOrderRequest;
import com.marketplace.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
@Transactional
public class RemovingItemOrderService {
    private final ItemRepository itemRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public void removeItem(ChangeOrderRequest changeRequest, Order order) {

        for (UserItem itemFromRequest : changeRequest.getRemovedItems()) {

            Item removedItem = itemRepository.findItemById(itemFromRequest.getItemId());
            boolean isContains = false;

            // find item in userOrder and delete it
            for (UserItem itemFromUserOrder : order.getUserItem()) {

                if (itemFromUserOrder.equals(itemFromRequest)) {
                    itemFromUserOrder.setUserId(changeRequest.getUserId());
                    itemFromUserOrder.setQuantity(itemFromRequest.getQuantity());

                    //return available quantity
                    removedItem.setAvailableQuantity(removedItem.getAvailableQuantity() + itemFromRequest.getQuantity());
                    isContains = true;
                    break;
                }
            }
            if (!isContains) {
                throw new ItemNotFoundException("Removed item doesn't exist");
            }
            entityManager.persist(removedItem);
        }
    }
}
