package com.marketplace.pojo;

import com.marketplace.entity.Item;

import java.util.List;

public class ItemMessage {

    private String resultMessage = "the products you bought: ";

    public ItemMessage(List<Item> itemList) {
        itemList.forEach(item -> resultMessage += "\n" + item.getName() + item.getDescription());
    }

    @Override
    public String toString() {
        return resultMessage.replace("[", "").replace("]", "");
    }
}
