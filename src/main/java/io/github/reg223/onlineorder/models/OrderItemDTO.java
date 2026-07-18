package io.github.reg223.onlineorder.models;

import io.github.reg223.onlineorder.entities.MenuEntity;
import io.github.reg223.onlineorder.entities.OrderEntity;

public record OrderItemDTO(
        Long orderId,
        Long menuItemId,
        Long shopId,
        Double price,
        Integer quantity,
        String itemName,
        String itemDescription,
        String itemImageUrl
) {
    public OrderItemDTO(OrderEntity order, MenuEntity menu) {
        this(
                order.id(),
                menu.id(),
                menu.shopId(),
                order.price(),
                order.quantity(),
                menu.name(),
                menu.description(),
                menu.imageUrl()
        );
    }
}
