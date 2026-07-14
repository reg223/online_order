package io.github.reg223.onlineorder.services;

import io.github.reg223.onlineorder.entities.CartEntity;
import io.github.reg223.onlineorder.entities.MenuEntity;
import io.github.reg223.onlineorder.entities.OrderEntity;
import io.github.reg223.onlineorder.models.CartDTO;
import io.github.reg223.onlineorder.models.OrderItemDTO;
import io.github.reg223.onlineorder.repos.CartRepository;
import io.github.reg223.onlineorder.repos.MenuRepository;
import io.github.reg223.onlineorder.repos.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    public CartService(CartRepository cartRepository,
                       MenuRepository menuRepository,
                       OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void addItemToCart(long customerId, long menuId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        MenuEntity menuItem = menuRepository.findById(menuId).orElse(null);
        if(menuItem == null) {
            return;
        }
        OrderEntity orderItem = orderRepository.findByCartIdAndMenuItemId(cart.id(), menuItem.id());

        Long orderItemId;
        int quantity;

        if (orderItem == null) {
            orderItemId = null;
            quantity = 1;
        } else {
            orderItemId = orderItem.id();
            quantity = orderItem.quantity()+1;
        }

        OrderEntity newItem = new OrderEntity(orderItemId,menuId,cart.id(), menuItem.price(), quantity);
        orderRepository.save(newItem);
        cartRepository.updateByCartId(cart.id(), cart.price()+menuItem.price());
    }

    public CartDTO getCart(long customerId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        List<OrderEntity> orders = orderRepository.findAllByCartId(cart.id());
        List<OrderItemDTO> orderItemDTOS = getOrderItemDTOs(orders);
        return new CartDTO(cart, orderItemDTOS);
    }

    @Transactional
    public void removeItemFromCart(long customerId, long menuId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        MenuEntity menuItem = menuRepository.findById(menuId).orElse(null);
        if(menuItem == null) {
            return;
        }
        OrderEntity orderItem = orderRepository.findByCartIdAndMenuItemId(cart.id(), menuItem.id());
        if(orderItem != null) {
            int quantity = orderItem.quantity()-1;
            if(quantity == 0) {
                orderRepository.deleteByCartIdAndMenuItemId(cart.id(),menuId);
            } else {
                orderRepository.save(new OrderEntity(orderItem.id(),menuId,cart.id(), menuItem.price(), quantity));
            }
            cartRepository.updateByCartId(cart.id(), cart.price()-menuItem.price());
        }
    }

    @Transactional
    public void ClearCart(long customerId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        orderRepository.deleteByCartId(cart.id());
        cartRepository.updateByCartId(cart.id(), 0.0);
    }

    private List<OrderItemDTO> getOrderItemDTOs(List<OrderEntity> orders) {
        Set<Long> menuIds = new HashSet<>();
        for (OrderEntity order : orders) {
            menuIds.add(order.menuItemId());
        }

        List<MenuEntity> menuEntities = menuRepository.findAllById(menuIds);
        Map<Long,MenuEntity> menuMap = new HashMap<>();
        for (MenuEntity menu : menuEntities) {
            menuMap.put(menu.id(), menu);
        }

        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderEntity order : orders) {
            orderItemDTOS.add(new OrderItemDTO(order,menuMap.get(order.menuItemId())));
        }
        return orderItemDTOS;
    }
}
