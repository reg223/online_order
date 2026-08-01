package io.github.reg223.onlineorder;


import io.github.reg223.onlineorder.entities.CartEntity;
import io.github.reg223.onlineorder.entities.MenuEntity;
import io.github.reg223.onlineorder.entities.OrderEntity;
import io.github.reg223.onlineorder.repos.CartRepository;
import io.github.reg223.onlineorder.repos.MenuRepository;
import io.github.reg223.onlineorder.repos.OrderRepository;
import io.github.reg223.onlineorder.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private OrderRepository orderRepository;



    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService(cartRepository,menuRepository,orderRepository);
    }

    @Test
    void addToCart_doNotExist_shouldCreate(){
        long customerId = 1L;
        long menuId = 1L;
        long cartId = 2L;

        CartEntity cartEntity = new CartEntity(cartId,customerId,0.0);
        MenuEntity menuEntity = new MenuEntity(menuId,1L,"name",6.7,"desc","url.jpg");

        Mockito.when(cartRepository.getByCustomerId(customerId)).thenReturn(cartEntity);
        Mockito.when(menuRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
        Mockito.when(orderRepository.findByCartIdAndMenuItemId(cartId,menuId)).thenReturn(null);

        cartService.addItemToCart(customerId,menuId);

        OrderEntity orderEntity = new OrderEntity(null,menuId,cartId,6.7,1);
        Mockito.verify(orderRepository).save(orderEntity);
        Mockito.verify(cartRepository).updateByCartId(cartId,6.7);

    }
}
