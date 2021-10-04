package com.marketplace.service;

import com.marketplace.entity.Item;
import com.marketplace.entity.Order;
import com.marketplace.entity.UserItem;
import com.marketplace.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchItemInOrdersService {
    private final OrderRepository orderRepository;
    private final SearchDuplicatesService searchDuplicatesService;


    public Map<Long, List<UserItem>> searchItem(Item item, boolean forceUpdate) {

        List<Order> allUsersOrders = orderRepository.findAll();
        Map<Long, List<UserItem>> matchesItemsOrder = new HashMap<>();

        for (Order order : allUsersOrders) {
            List<UserItem> userItemList = order.getUserItem();
            List<UserItem> matchedItem=searchDuplicatesService.searchDuplicates(userItemList,item,forceUpdate);

            matchesItemsOrder.put(order.getId(),matchedItem);

        }
        return matchesItemsOrder;
    }
}
