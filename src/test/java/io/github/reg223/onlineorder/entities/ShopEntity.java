package io.github.reg223.onlineorder.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("shops")
public record ShopEntity(
        @Id Long id,
        String name,
        String address,
        String imgUrl,
        String phone
) {
}
