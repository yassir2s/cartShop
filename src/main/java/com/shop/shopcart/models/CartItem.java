package com.shop.shopcart.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    String nameProduct;
    int quantity;
    double price;
    double total;
}
