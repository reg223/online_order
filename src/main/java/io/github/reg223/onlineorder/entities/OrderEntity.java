package io.github.reg223.onlineorder.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("order_items")
public record OrderEntity(
        @Id Long id,
        Long menuItemId,
        Long cartId,
        Double price,
        Integer quantity
) {
}

