package com.Retail.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Retail.model.ProductList;
import com.Retail.service.ProductListService;

@Controller
@ResponseBody
@RequestMapping("/products-lists")
public class ProductListController {
    private final ProductListService productService;

    public ProductListController(ProductListService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductList> getProducts() {
        return productService.getAllProducts();
    }
}
