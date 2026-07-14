package io.github.reg223.onlineorder;


import io.github.reg223.onlineorder.entities.*;
import io.github.reg223.onlineorder.models.ShopDTO;
import io.github.reg223.onlineorder.repos.*;
import io.github.reg223.onlineorder.services.CartService;
import io.github.reg223.onlineorder.services.MenuItemService;
import io.github.reg223.onlineorder.services.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DevRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DevRunner.class);

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;

    private final CartService cartService;
    private final MenuItemService menuItemService;
    private final ShopService shopService;

    public DevRunner(
            CartRepository cartRepository,
            MenuRepository menuRepository,
            OrderRepository orderRepository,
            ShopRepository shopRepository,
            CustomerRepository customerRepository,
            CartService cartService,
            MenuItemService menuItemService,
            ShopService shopService
    ){
        this.cartRepository = cartRepository;
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.shopRepository = shopRepository;
        this.customerRepository = customerRepository;

        this.cartService = cartService;
        this.menuItemService = menuItemService;
        this.shopService = shopService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("DevRunner inserting mock data...");
        CustomerEntity customer01 = new CustomerEntity(null, "sample1@admin.com","sample1",
                                               true,"John","Doe");
        customerRepository.save(customer01);

        CustomerEntity customer02 = new CustomerEntity(null, "sample2@admin.com","sample2",
                true,"Mary","Jane");
        customerRepository.save(customer02);


        cartRepository.saveAll(List.of(new CartEntity(null,1L,0.0),
                                       new CartEntity(null, 2L, 0.0)));

        List<ShopEntity> shops = List.of(new ShopEntity(null,"shop01","add01","https://s01.jpg",
                                             "7811234567"),
                                         new ShopEntity(null,"shop02","add02","https://s02.jpg",
                                             "7812345678"),
                                         new ShopEntity(null,"shop03","add03","https://s03.jpg",
                                             "7813456789"));
        shopRepository.saveAll(shops);

        List<MenuEntity> items = List.of(new MenuEntity(null,1L,"food1A", 42.0,"desc1A",
                                                "https://f4A.png"),
                                         new MenuEntity(null,2L,"food2A", 42.1,"desc4B",
                                                "https://f4B.png"),
                                         new MenuEntity(null,1L,"food1B", 42.2,"desc4C",
                                                "https://f4C.png"),
                                         new MenuEntity(null,2L,"food2B", 42.3,"desc5A",
                                                "https://f5A.png"),
                                         new MenuEntity(null,2L,"food2C", 42.4,"desc5B",
                                                "https://f5B.png"),
                                         new MenuEntity(null,3L,"food3A", 42.5,"desc5C",
                                                "https://f5C.png"));
        menuRepository.saveAll(items);

//        List<OrderEntity> orders = List.of(new OrderEntity(null, 1L, 1L, 3.0, 2),
//                                           new OrderEntity(null, 1L, 1L, 5.0, 1),
//                                           new OrderEntity(null, 2L, 1L, 7.0, 8),
//                                           new OrderEntity(null, 3L, 1L, 100.0, 56),
//                                           new OrderEntity(null, 2L, 1L, 2.0, 2),
//                                           new OrderEntity(null, 1L, 1L, 4.0, 12),
//                                           new OrderEntity(null, 3L, 1L, 6.0, 30));
        List<OrderEntity> orders = new ArrayList<>();
        orderRepository.saveAll(orders);

        System.out.println("DevRunner finished inserting mock data.");


        System.out.println("DevRunner testing item update and deletion...");

        customerRepository.deleteById(2L);
//        shopRepository.deleteById(1L);
        customerRepository.updateNameByEmail("sample01@admin.com","Mary", "Doe");


        System.out.println("DevRunner finished item update and deletion tests.");

        System.out.println("Listing Shops");
        List<ShopDTO> shopDTOS = shopService.getShops();
        logger.info(shopDTOS.toString());

        System.out.println("Listing menu from shop #2");
        List<MenuEntity> menuEntities = menuItemService.getMenuByShopId(2L);
        logger.info(menuEntities.toString());
        System.out.println("Listing menu #2");
        logger.info(menuItemService.getMenuById(2L).toString());

        System.out.println("Listing cart content before insertion");
        logger.info(cartService.getCart(1L).toString());

        cartService.addItemToCart(1L,4L);
        cartService.addItemToCart(1L,4L);
        cartService.addItemToCart(1L,4L);
        cartService.addItemToCart(1L,2L);


        System.out.println("Listing cart content after insertion");
        logger.info(cartService.getCart(1L).toString());

        cartService.removeItemFromCart(1L,2L);

        System.out.println("Listing cart content after one removal");
        logger.info(cartService.getCart(1L).toString());

        cartService.ClearCart(1L);
        System.out.println("Listing cart content after full removal");
        logger.info(cartService.getCart(1L).toString());
    }




}
