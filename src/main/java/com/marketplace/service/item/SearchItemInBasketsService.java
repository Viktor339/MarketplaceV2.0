package com.marketplace.service.item;


import com.marketplace.entity.Basket;
import com.marketplace.entity.Item;
import com.marketplace.entity.UserItem;
import com.marketplace.repository.BasketRepository;
import com.marketplace.service.useritem.SearchDuplicatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchItemInBasketsService {

    private final BasketRepository basketRepository;
    private final SearchDuplicatesService searchDuplicatesService;

    public  Map<Long, List<UserItem>> searchItem(Item item, boolean forceUpdate) {

        List<Basket> allUsersBaskets = basketRepository.findAll();
        Map<Long, List<UserItem>> duplicatedItemsInAllBaskets = new HashMap<>();

        for (Basket basket : allUsersBaskets) {
            List<UserItem> userItemList = basket.getUserItem();
            List<UserItem> duplicatedItems=searchDuplicatesService.searchDuplicates(userItemList,item,forceUpdate);

            duplicatedItemsInAllBaskets.put(basket.getId(),duplicatedItems);
        }
        return duplicatedItemsInAllBaskets;
    }
}
