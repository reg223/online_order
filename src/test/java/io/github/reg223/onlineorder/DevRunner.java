package io.github.reg223.onlineorder;


import io.github.reg223.onlineorder.entities.*;
import io.github.reg223.onlineorder.repos.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DevRunner implements ApplicationRunner {
    public final CartRepository cartRepository;
    public final MenuRepository menuRepository;
    public final OrderRepository orderRepository;
    public final CustomerRepository customerRepository;
    public final ShopRepository shopRepository;

    public DevRunner(
            CartRepository cartRepository,
            MenuRepository menuRepository,
            OrderRepository orderRepository,
            ShopRepository shopRepository,
            CustomerRepository customerRepository
    ){
        this.cartRepository = cartRepository;
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.shopRepository = shopRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
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

        List<MenuEntity> items = List.of(new MenuEntity(null,4L,"food4A", 42.0,"desc4A",
                                                "https://f4A.png"),
                                         new MenuEntity(null,4L,"food4B", 42.1,"desc4B",
                                                "https://f4B.png"),
                                         new MenuEntity(null,4L,"food4C", 42.2,"desc4C",
                                                "https://f4C.png"),
                                         new MenuEntity(null,5L,"food5A", 42.3,"desc5A",
                                                "https://f5A.png"),
                                         new MenuEntity(null,5L,"food5B", 42.4,"desc5B",
                                                "https://f5B.png"),
                                         new MenuEntity(null,5L,"food5C", 42.5,"desc5C",
                                                "https://f5C.png"));
        menuRepository.saveAll(items);

        List<OrderEntity> orders = List.of(new OrderEntity(null, 1L, 1L, 3.0, 2),
                                           new OrderEntity(null, 2L, 1L, 5.0, 1),
                                           new OrderEntity(null, 3L, 1L, 7.0, 8),
                                           new OrderEntity(null, 4L, 1L, 100.0, 56),
                                           new OrderEntity(null, 5L, 1L, 2.0, 2),
                                           new OrderEntity(null, 6L, 1L, 4.0, 12),
                                           new OrderEntity(null, 7L, 1L, 6.0, 30));
        orderRepository.saveAll(orders);
    }




}
