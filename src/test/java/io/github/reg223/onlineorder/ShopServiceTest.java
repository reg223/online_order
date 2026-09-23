package io.github.reg223.onlineorder;

import io.github.reg223.onlineorder.entities.MenuEntity;
import io.github.reg223.onlineorder.entities.ShopEntity;
import io.github.reg223.onlineorder.models.ShopDTO;
import io.github.reg223.onlineorder.repos.MenuRepository;
import io.github.reg223.onlineorder.repos.ShopRepository;
import io.github.reg223.onlineorder.services.ShopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {

    @Mock
    private ShopRepository shopRepository;
    @Mock
    private MenuRepository menuRepository;

    private ShopService shopService;

    @BeforeEach
    void setUp() {
        shopService = new ShopService(shopRepository, menuRepository);
    }

    @Test
    void getShops_shouldReturnShopsWithMenuItems(){
        List<ShopEntity> shopEntities = List.of(
                new ShopEntity(1L,"shop1","address.1","shop-url.1","phone.1"),
                new ShopEntity(2L,"shop2","address.2","shop-url.2","phone.2")
        );
        List<MenuEntity> menuEntities = List.of(
                new MenuEntity(1L,1L,"menu1",10.0,"desc.1","url.1"),
                new MenuEntity(2L,1L,"menu2",12.0,"desc.2","url.2"),
                new MenuEntity(3L,2L,"menu3",14.0,"desc.3","url.3")
        );

        Mockito.when(shopRepository.findAll()).thenReturn(shopEntities);
        Mockito.when(menuRepository.findAll()).thenReturn(menuEntities);

        List<ShopDTO> shops = shopService.getShops();

        Assertions.assertEquals(shopEntities.size(), shops.size());
        Assertions.assertEquals(1L, shops.getFirst().id());
        Assertions.assertEquals("shop1", shops.getFirst().name());
        Assertions.assertEquals("address.1", shops.getFirst().address());
        Assertions.assertEquals("phone.1", shops.getFirst().phone());
        Assertions.assertEquals("shop-url.1", shops.getFirst().imageUrl());
        Assertions.assertEquals(2, shops.getFirst().items().size());
        Assertions.assertEquals("menu1", shops.getFirst().items().getFirst().name());
        Assertions.assertEquals("desc.1", shops.getFirst().items().getFirst().description());
        Assertions.assertEquals("url.1", shops.getFirst().items().getFirst().imageUrl());
        Assertions.assertEquals(10.0, shops.getFirst().items().getFirst().price());
        Assertions.assertEquals(2L, shops.get(1).id());
        Assertions.assertEquals(1, shops.get(1).items().size());
        Assertions.assertEquals("menu3", shops.get(1).items().getFirst().name());

    }

    @Test
    void getShops_noMenuItems_shouldReturnShopWithNullItems(){
        List<ShopEntity> shopEntities = List.of(
                new ShopEntity(1L,"shop1","address.1","shop-url.1","phone.1")
        );

        Mockito.when(shopRepository.findAll()).thenReturn(shopEntities);
        Mockito.when(menuRepository.findAll()).thenReturn(List.of());

        List<ShopDTO> shops = shopService.getShops();

        Assertions.assertEquals(1, shops.size());
        Assertions.assertEquals(1L, shops.getFirst().id());
        Assertions.assertNull(shops.getFirst().items());

    }
}
