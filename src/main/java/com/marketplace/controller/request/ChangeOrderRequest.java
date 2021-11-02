package com.marketplace.controller.request;

import com.marketplace.entity.UserItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeOrderRequest {
    @Schema(description = "userId", example = "1")
    private Long userId;
    @Schema(description = "status", example = "SENT")
    private String status;
    @Schema(description = "addedItems", example = "[\n" +
            "      {\n" +
            "    \"itemId\": \"3\",\n" +
            "    \"quantity\": \"1\"\n" +
            "  }\n" +
            "  ]")
    private UserItem[]addedItems;
    @Schema(description = "removedItems", example = "[\n" +
            "      {\n" +
            "    \"itemId\": \"3\",\n" +
            "    \"quantity\": \"1\"\n" +
            "  }\n" +
            "  ]")
    private UserItem[]removedItems;

}
