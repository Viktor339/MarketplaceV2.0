package com.marketplace.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeItemRequest {
    @Schema(description = "itemId", example = "1")
    private Long itemId;
    @Schema(description = "newItemName", example = "newItemName")
    private String newItemName;
    @Schema(description = "newItemDescription", example = "newItemDescription")
    private String newItemDescription;
    @Schema(description = "newItemTags", example = "newItemTags")
    private String newItemTags;
    @Schema(description = "quantity", example = "3")
    private Long quantity;
    @Schema(description = "forceUpdate", example = "false")
    private boolean forceUpdate;
}
