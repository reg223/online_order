package io.github.reg223.onlineorder.services;

import io.github.reg223.onlineorder.entities.MenuEntity;
import io.github.reg223.onlineorder.repos.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
    private final MenuRepository menuRepository;

    public MenuItemService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuEntity> getMenuByShopId(long shopId) {
        return menuRepository.getByShopId(shopId);
    }

    public MenuEntity getMenuById(long id) {
        return menuRepository.findById(id).orElse(null);
    }
}
