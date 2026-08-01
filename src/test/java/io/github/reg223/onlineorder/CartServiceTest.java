package io.github.reg223.onlineorder;


import io.github.reg223.onlineorder.entities.CartEntity;
import io.github.reg223.onlineorder.entities.MenuEntity;
import io.github.reg223.onlineorder.entities.OrderEntity;
import io.github.reg223.onlineorder.models.CartDTO;
import io.github.reg223.onlineorder.repos.CartRepository;
import io.github.reg223.onlineorder.repos.MenuRepository;
import io.github.reg223.onlineorder.repos.OrderRepository;
import io.github.reg223.onlineorder.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Test
    void addToCart_doesExist_shouldUpdate(){
        long customerId = 1L;
        long menuId = 1L;
        long cartId = 2L;

        CartEntity cartEntity = new CartEntity(cartId,customerId,8.0);
        MenuEntity menuEntity = new MenuEntity(menuId,1L,"name",6.7,"desc","url.jpg");
        OrderEntity orderEntity = new OrderEntity(3L,menuId,cartId,6.7,2);

        Mockito.when(cartRepository.getByCustomerId(customerId)).thenReturn(cartEntity);
        Mockito.when(menuRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
        Mockito.when(orderRepository.findByCartIdAndMenuItemId(cartId,menuId)).thenReturn(orderEntity);

        cartService.addItemToCart(customerId,menuId);

        OrderEntity updatedOrderEntity = new OrderEntity(3L,menuId,cartId,6.7,3);
        Mockito.verify(orderRepository).save(updatedOrderEntity);
        Mockito.verify(cartRepository).updateByCartId(cartId,14.7);

    }

    @Test
    void addToCart_menuDoesNotExist_shouldDoNothing(){
        long customerId = 1L;
        long menuId = 1L;
        long cartId = 2L;

        CartEntity cartEntity = new CartEntity(cartId,customerId,8.0);

        Mockito.when(cartRepository.getByCustomerId(customerId)).thenReturn(cartEntity);
        Mockito.when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        cartService.addItemToCart(customerId,menuId);

        Mockito.verify(orderRepository, Mockito.never()).findByCartIdAndMenuItemId(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(cartRepository, Mockito.never()).updateByCartId(Mockito.anyLong(), Mockito.anyDouble());

    }

    @Test
    void getCart_shouldReturnCartItems(){
        long customerId = 1L;
        long cartId = 2L;

        CartEntity cartEntity = new CartEntity(cartId,customerId,22.0);
        List<OrderEntity> orderEntities = List.of(
                new OrderEntity(1L,1L,cartId,10.0,1),
                new OrderEntity(2L,2L,cartId,12.0,1)
        );

        List<MenuEntity> menuEntities = List.of(
                new MenuEntity(1L,1L,"menu1", 10.0,"desc.1","url.1"),
                new MenuEntity(2L,1L,"menu2", 12.0,"desc.2","url.2")
        );

        Mockito.when(cartRepository.getByCustomerId(customerId)).thenReturn(cartEntity);
        Mockito.when(orderRepository.findAllByCartId(cartEntity.id())).thenReturn(orderEntities);
        Mockito.when(menuRepository.findAllById(Set.of(1L, 2L))).thenReturn(menuEntities);

        CartDTO cart = cartService.getCart(customerId);

        Assertions.assertEquals(cartId, cart.id());
        Assertions.assertEquals(cartEntity.price(), cart.totalPrice());
        Assertions.assertEquals(orderEntities.size(), cart.items().size());
        Assertions.assertEquals("menu1", cart.items().get(0).itemName());
        Assertions.assertEquals("desc.1", cart.items().get(0).itemDescription());
        Assertions.assertEquals("url.1", cart.items().get(0).itemImageUrl());
        Assertions.assertEquals("menu2", cart.items().get(1).itemName());

    }

    @Test
    void removeFromCart_quantityGreaterThanOne_shouldUpdate(){
        long customerId = 1L;
        long menuId = 1L;
        long cartId = 2L;

        CartEntity cartEntity = new CartEntity(cartId,customerId,20.0);
        MenuEntity menuEntity = new MenuEntity(menuId,1L,"name",6.7,"desc","url.jpg");
        OrderEntity orderEntity = new OrderEntity(3L,menuId,cartId,6.7,2);

        Mockito.when(cartRepository.getByCustomerId(customerId)).thenReturn(cartEntity);
        Mockito.when(menuRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
        Mockito.when(orderRepository.findByCartIdAndMenuItemId(cartId,menuId)).thenReturn(orderEntity);

        cartService.removeItemFromCart(customerId,menuId);

        OrderEntity updatedOrderEntity = new OrderEntity(3L,menuId,cartId,6.7,1);
        Mockito.verify(orderRepository).save(updatedOrderEntity);
        Mockito.verify(orderRepository, Mockito.never()).deleteByCartIdAndMenuItemId(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(cartRepository).updateByCartId(cartId,13.3);

    }

    @Test
    void removeFromCart_quantityOne_shouldDelete(){
        long customerId = 1L;
        long menuId = 1L;
        long cartId = 2L;

        CartEntity cartEntity = new CartEntity(cartId,customerId,6.7);
        MenuEntity menuEntity = new MenuEntity(menuId,1L,"name",6.7,"desc","url.jpg");
        OrderEntity orderEntity = new OrderEntity(3L,menuId,cartId,6.7,1);

        Mockito.when(cartRepository.getByCustomerId(customerId)).thenReturn(cartEntity);
        Mockito.when(menuRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
        Mockito.when(orderRepository.findByCartIdAndMenuItemId(cartId,menuId)).thenReturn(orderEntity);

        cartService.removeItemFromCart(customerId,menuId);

        Mockito.verify(orderRepository).deleteByCartIdAndMenuItemId(cartId,menuId);
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(cartRepository).updateByCartId(cartId,0.0);

    }

    @Test
    void removeFromCart_orderDoesNotExist_shouldDoNothing(){
        long customerId = 1L;
        long menuId = 1L;
        long cartId = 2L;

        CartEntity cartEntity = new CartEntity(cartId,customerId,6.7);
        MenuEntity menuEntity = new MenuEntity(menuId,1L,"name",6.7,"desc","url.jpg");

        Mockito.when(cartRepository.getByCustomerId(customerId)).thenReturn(cartEntity);
        Mockito.when(menuRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
        Mockito.when(orderRepository.findByCartIdAndMenuItemId(cartId,menuId)).thenReturn(null);

        cartService.removeItemFromCart(customerId,menuId);

        Mockito.verify(orderRepository, Mockito.never()).deleteByCartIdAndMenuItemId(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(cartRepository, Mockito.never()).updateByCartId(Mockito.anyLong(), Mockito.anyDouble());

    }

    @Test
    void clearCart_shouldDeleteItemsAndResetPrice(){
        long customerId = 1L;
        long cartId = 2L;

        CartEntity cartEntity = new CartEntity(cartId,customerId,25.0);

        Mockito.when(cartRepository.getByCustomerId(customerId)).thenReturn(cartEntity);

        cartService.ClearCart(customerId);

        Mockito.verify(orderRepository).deleteByCartId(cartId);
        Mockito.verify(cartRepository).updateByCartId(cartId,0.0);

    }
}
