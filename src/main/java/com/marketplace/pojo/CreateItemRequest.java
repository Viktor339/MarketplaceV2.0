package com.marketplace.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateItemRequest {
    @Schema(description = "name", example = "name")
    private String name;
    @Schema(description = "description", example = "description")
    private String description;
    @Schema(description = "tags", example = "tags")
    private String tags;
    @Schema(description = "availableQuantity", example = "1")
    private Long availableQuantity;
}
