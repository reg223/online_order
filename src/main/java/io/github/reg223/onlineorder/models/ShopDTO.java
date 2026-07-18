package io.github.reg223.onlineorder.models;

import io.github.reg223.onlineorder.entities.ShopEntity;

import java.util.List;

public record ShopDTO(
        Long id,
        String name,
        String address,
        String phone,
        String imageUrl,
        List<MenuItemDTO> items
) {
    public ShopDTO(ShopEntity shop, List<MenuItemDTO> items){
        this(shop.id(), shop.name(), shop.address(), shop.phone(), shop.imageUrl(), items);
    }
}
