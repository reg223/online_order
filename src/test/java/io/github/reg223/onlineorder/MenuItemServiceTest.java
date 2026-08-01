package io.github.reg223.onlineorder;

import io.github.reg223.onlineorder.entities.MenuEntity;
import io.github.reg223.onlineorder.repos.MenuRepository;
import io.github.reg223.onlineorder.services.MenuItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MenuItemServiceTest {

    @Mock
    private MenuRepository menuRepository;

    private MenuItemService menuItemService;

    @BeforeEach
    void setUp() {
        menuItemService = new MenuItemService(menuRepository);
    }

    @Test
    void getMenuByShopId_shouldReturnMenuItems(){
        long shopId = 1L;
        List<MenuEntity> menuEntities = List.of(
                new MenuEntity(1L,shopId,"menu1",10.0,"desc.1","url.1"),
                new MenuEntity(2L,shopId,"menu2",12.0,"desc.2","url.2")
        );

        Mockito.when(menuRepository.getByShopId(shopId)).thenReturn(menuEntities);

        List<MenuEntity> menus = menuItemService.getMenuByShopId(shopId);

        Assertions.assertEquals(menuEntities, menus);

    }

    @Test
    void getMenuById_doesExist_shouldReturnMenuItem(){
        long menuId = 1L;
        MenuEntity menuEntity = new MenuEntity(menuId,1L,"menu1",10.0,"desc.1","url.1");

        Mockito.when(menuRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));

        MenuEntity menu = menuItemService.getMenuById(menuId);

        Assertions.assertEquals(menuEntity, menu);

    }

    @Test
    void getMenuById_doesNotExist_shouldReturnNull(){
        long menuId = 1L;

        Mockito.when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        MenuEntity menu = menuItemService.getMenuById(menuId);

        Assertions.assertNull(menu);

    }
}
