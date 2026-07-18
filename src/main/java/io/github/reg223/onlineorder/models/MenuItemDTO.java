package io.github.reg223.onlineorder.models;

import io.github.reg223.onlineorder.entities.MenuEntity;

public record MenuItemDTO(
        Long id,
        String name,
        String description,
        String imageUrl,
        Double price
){
    public MenuItemDTO(MenuEntity menu){
        this(menu.id(), menu.name(), menu.description(), menu.imageUrl(), menu.price());
    }
}
