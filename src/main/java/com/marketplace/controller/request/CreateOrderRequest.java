package com.marketplace.controller.request;

import com.marketplace.entity.UserItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateOrderRequest {
    @Schema(description = "userId", example = "1")
    private Long userId;
    @Schema(description = "status", example = "IN_PROCESSING")
    private String status;
    @Schema(description = "addedItems", example = "[\n" +
            "      {\n" +
            "    \"itemId\": \"2\",\n" +
            "    \"quantity\": \"1\"\n" +
            "  }\n" +
            "  ]")
    private UserItem[]addedItems;

}
