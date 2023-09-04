package com.shop.shopcart.models;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class ShopCart {

    private Map<String, Double> items;
}
