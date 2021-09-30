package com.marketplace.service;

import com.marketplace.entity.Basket;
import com.marketplace.entity.Item;
import com.marketplace.entity.Order;
import com.marketplace.entity.UserItem;
import com.marketplace.exeption.ItemAlreadyInTheUserBasketException;
import com.marketplace.repository.BasketRepository;
import com.marketplace.repository.ItemRepository;
import com.marketplace.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SearchItemService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final BasketRepository basketRepository;

    public boolean searchItem(Item item) {

        // try to find item in all orders
        List<Order> allUsersOrders = orderRepository.findAll();
        Set<Item> uniqueItem = new HashSet<>();
        for (Order order : allUsersOrders) {
            List<UserItem> userItemList = order.getItems();
            for (UserItem userItem : userItemList) {
                Item itemFromRepository = itemRepository.findItemById(userItem.getItemId());
                uniqueItem.add(itemFromRepository);
            }
        }

        // try to find item in all baskets
        List<Basket> allUsersBaskets = basketRepository.findAll();
        for (Basket basket : allUsersBaskets) {
            List<UserItem> userItemList = basket.getUserItem();
            for (UserItem userItem : userItemList) {
                Item itemFromRepository = itemRepository.findItemById(userItem.getItemId());
                uniqueItem.add(itemFromRepository);
            }
        }

        if (uniqueItem.contains(item)) {
            throw new ItemAlreadyInTheUserBasketException("This product is in the user's basket or order");
        }
        return false;
    }
}
