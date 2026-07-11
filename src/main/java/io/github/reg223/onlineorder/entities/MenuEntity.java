package io.github.reg223.onlineorder.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("menu_items")
public record MenuEntity(
        @Id Long id,
        Long shopId,
        String name,
        Double price,
        String description,
        String imageUrl
) {
}
