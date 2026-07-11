package io.github.reg223.onlineorder.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("carts")
public record CartEntity(
        @Id Long id,
        Long customerId,
        Double price
) {
}
