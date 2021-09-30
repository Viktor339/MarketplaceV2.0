package com.marketplace.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeItemRequest {
    private Long itemId;
    private String newItemName;
    private String newItemDescription;
    private String newItemTags;
    private Long quantity;
    private boolean forceUpdate;
}
