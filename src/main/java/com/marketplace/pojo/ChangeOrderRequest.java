package com.marketplace.pojo;

import com.marketplace.entity.UserItem;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeOrderRequest {
    private Long userId;
    private String status;
    private UserItem[]addedItems;
    private UserItem[]removedItems;

}
