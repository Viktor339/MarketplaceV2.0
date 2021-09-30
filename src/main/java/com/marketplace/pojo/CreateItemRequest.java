package com.marketplace.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateItemRequest {
    private String name;
    private String description;
    private String tags;
    private Long availableQuantity;
}
