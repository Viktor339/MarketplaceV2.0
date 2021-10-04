package com.marketplace.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddItemRequest {
    @Schema(description = "id", example = "1")
    private Long id;
    @Schema(description = "addedQuantity", example = "1")
    private Long addedQuantity;
}
