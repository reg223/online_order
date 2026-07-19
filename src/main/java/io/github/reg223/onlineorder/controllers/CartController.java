package io.github.reg223.onlineorder.controllers;

import io.github.reg223.onlineorder.models.CartBody;
import io.github.reg223.onlineorder.models.CartDTO;
import io.github.reg223.onlineorder.services.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public CartDTO getCart() {
        return cartService.getCart(1L);
    }

    @PostMapping(value = "/cart", params = "action=add")
    public void addItem(@RequestBody CartBody body) {
        cartService.addItemToCart(1L, body.menuId());
    }

    @PostMapping(value = "/cart", params = "action=remove")
    public void removeItem(@RequestBody(required = false) CartBody body) {
        if(body == null) return;
        cartService.removeItemFromCart(1L, body.menuId());
    }

    @PostMapping("cart/checkout")
    public void checkout() {
        cartService.ClearCart(1L);
    }
}
