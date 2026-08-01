package io.github.reg223.onlineorder.controllers;

import io.github.reg223.onlineorder.entities.CustomerEntity;
import io.github.reg223.onlineorder.models.CartBody;
import io.github.reg223.onlineorder.models.CartDTO;
import io.github.reg223.onlineorder.services.CartService;
import io.github.reg223.onlineorder.services.CustomerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;

    public CartController(CartService cartService, CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @GetMapping("/cart")
    public CartDTO getCart(@AuthenticationPrincipal User user) {
        CustomerEntity customer  = customerService.getCustomerByEmail(user.getUsername());
        return cartService.getCart(customer.id());
    }

    @PostMapping(value = "/cart", params = "action=add")
    public void addItem(@AuthenticationPrincipal User user, @RequestBody CartBody body) {
        CustomerEntity customer  = customerService.getCustomerByEmail(user.getUsername());
        cartService.addItemToCart(customer.id(), body.menuId());
    }

    @PostMapping(value = "/cart", params = "action=remove")
    public void removeItem(@AuthenticationPrincipal User user, @RequestBody(required = false) CartBody body) {
        if(body == null) return;
        CustomerEntity customer  = customerService.getCustomerByEmail(user.getUsername());
        cartService.removeItemFromCart(customer.id(), body.menuId());
    }

    @PostMapping("cart/checkout")
    public void checkout(@AuthenticationPrincipal User user) {
        CustomerEntity customer  = customerService.getCustomerByEmail(user.getUsername());
        cartService.ClearCart(customer.id());
    }
}
