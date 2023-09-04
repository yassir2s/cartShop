package com.shop.shopcart.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class CartSummry {

    List<CartItem> items;
    Double total;
}
