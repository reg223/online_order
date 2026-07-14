package io.github.reg223.onlineorder.services;

import io.github.reg223.onlineorder.entities.MenuEntity;
import io.github.reg223.onlineorder.entities.ShopEntity;
import io.github.reg223.onlineorder.models.MenuItemDTO;
import io.github.reg223.onlineorder.models.ShopDTO;
import io.github.reg223.onlineorder.repos.MenuRepository;
import io.github.reg223.onlineorder.repos.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;

    public ShopService(ShopRepository shopRepository,
                       MenuRepository menuRepository) {
        this.shopRepository = shopRepository;
        this.menuRepository = menuRepository;
    }

    public List<ShopDTO> getShops() {
        List<ShopEntity> shops = shopRepository.findAll();
        List<MenuEntity> menuEntities = menuRepository.findAll();
        Map<Long, List<MenuItemDTO>> menus = new HashMap<>();
        for (MenuEntity menu : menuEntities) {
            List<MenuItemDTO> group = menus.computeIfAbsent(menu.shopId(),
                    k -> new ArrayList<>());
            MenuItemDTO entry = new MenuItemDTO(menu);
            group.add(entry);
        }

        List<ShopDTO> shopDTOs = new ArrayList<>();

        for(ShopEntity shop : shops) {
            shopDTOs.add(new ShopDTO(shop,menus.get(shop.id())));
        }
        return shopDTOs;
    }

}
