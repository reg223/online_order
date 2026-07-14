package io.github.reg223.onlineorder.models;

import io.github.reg223.onlineorder.entities.CartEntity;

import java.util.List;

public record CartDTO(
        Long id,
        Double totalPrice,
        List<OrderItemDTO> items
) {
    public CartDTO(CartEntity cart, List<OrderItemDTO> items){
        this(cart.id(), cart.price(), items);
    }
}
