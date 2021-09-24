package com.marketplace.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddItemRequest {
    private Long id;
    private Long addedQuantity;
}
