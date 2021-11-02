package com.marketplace.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteItemRequest {
    @Schema(description = "id", example = "1")
    private Long id;
    @Schema(description = "forceUpdate", example = "false")
    private boolean forceUpdate;
}
