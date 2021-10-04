package com.marketplace.service;

import com.marketplace.entity.Item;
import com.marketplace.entity.UserItem;
import com.marketplace.exeption.ItemAlreadyInTheUserBasketException;
import com.marketplace.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchDuplicatesService {
    private final ItemRepository itemRepository;

    public List<UserItem> searchDuplicates(List<UserItem> userItemList,
                                                 Item item,
                                                 boolean forceUpdate){

        List<UserItem> duplicatedItems = new ArrayList<>();

        for (UserItem userItem : userItemList) {
            Item itemFromRepository = itemRepository.findItemById(userItem.getItemId());

            if (itemFromRepository.equals(item) & !forceUpdate) {
                throw new ItemAlreadyInTheUserBasketException("This product is in the user's basket or order");
            }

            if (itemFromRepository.equals(item) & forceUpdate) {
                duplicatedItems.add(userItem);
            }
        }
        return  duplicatedItems;
    }
}
