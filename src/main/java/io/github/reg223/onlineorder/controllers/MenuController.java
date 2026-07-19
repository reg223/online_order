package io.github.reg223.onlineorder.controllers;

import io.github.reg223.onlineorder.entities.MenuEntity;
import io.github.reg223.onlineorder.models.ShopDTO;
import io.github.reg223.onlineorder.services.MenuItemService;
import io.github.reg223.onlineorder.services.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    private final ShopService shopService;
    private final MenuItemService menuItemService;
    public MenuController(ShopService shopService, MenuItemService menuItemService) {
        this.shopService = shopService;
        this.menuItemService = menuItemService;
    }

    @GetMapping("/shops/{shopId}/menu")
    public List<MenuEntity> getMenusByShop(@PathVariable("shopId") long shopId) {
        return menuItemService.getMenuByShopId(shopId);
    }

    @GetMapping("/shops/all")
    public List<ShopDTO> getAllMenus() {
        return  shopService.getShops();
    }
}
