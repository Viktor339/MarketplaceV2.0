package com.marketplace.pojo;

import com.marketplace.entity.Item;

import java.util.Map;

public class ItemMessage {

    private String resultMessage = "the products you bought: ";

    public ItemMessage(Map<Item,Long> itemList) {
        for (Map.Entry<Item, Long> entry : itemList.entrySet()) {
            resultMessage += "\n" +" product: " + entry.getKey().getName()
                    +" description: "+entry.getKey().getDescription()
                    +" quantity: "+ entry.getValue();
        }
    }

    @Override
    public String toString() {
        return resultMessage.replace("[", "").replace("]", "");
    }
}
