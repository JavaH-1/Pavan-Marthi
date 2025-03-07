package com.Retail.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Retail.model.CartItem;
import com.Retail.service.CartService;

@Controller
@ResponseBody
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    
    public List<CartItem> getCart() {
        return cartService.getCartItems();
    }

    @PostMapping
    public String addToCart(@RequestBody CartItem item) {
        cartService.addToCart(item);
        return "Item added to cart successfully";
    }
    
    @DeleteMapping
    public String clearCart() {
        cartService.clearCart();
        return "Cart cleared successfully";
    }
}
