package com.marketplace.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteOrderRequest {
    @Schema(description = "orderId", example = "1")
    private Long orderId;
}
