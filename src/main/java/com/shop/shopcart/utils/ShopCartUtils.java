package com.shop.shopcart.utils;

import com.shop.shopcart.models.CartItem;
import com.shop.shopcart.models.CartSummry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ShopCartUtils {
    public static final int INPUT_INVALID = -1;

    private ShopCartUtils(){

    }

    public static int getPositiveInteger(String choice){
        int choiceInteger = INPUT_INVALID;
        if(choice == null) {
            return INPUT_INVALID;
        }
        try {
            int choiceTmp = Integer.parseInt(choice);
            choiceInteger = choiceTmp < 0 ? INPUT_INVALID : choiceTmp ;
        }catch (NumberFormatException ex){
            return choiceInteger;
        }
        return choiceInteger;
    }

    public static boolean validQuantity(int quantity){
        return quantity > 0;
    }

    public static boolean validQuantityTo(int quantity){
        return quantity > 0;
    }

    public static void validateChoice(int choice) {
        if(choice == -1)
        {
            System.out.println("your choice is invalid");
            return;
        }
        System.out.println("your choice is : " + choice);
    }

    public static boolean addItemToCartItems(String itemToAdd, Map<String,String> items, Map<String, Integer> cartItems) {
        if(items.containsKey(itemToAdd.toUpperCase())){
            if(!cartItems.containsKey(itemToAdd.toUpperCase())){
                cartItems.put(itemToAdd.toUpperCase(), 0);
            }
            return true;
        }
        System.out.println("Please enter an available product");
        return false;
    }

    public static void deleteItemFromCartItems(String itemToDelete, Map<String, Integer> cartItems) {
        if(cartItems.containsKey(itemToDelete.toUpperCase())){
            cartItems.remove(itemToDelete.toUpperCase());
            return;
        }
        System.out.println("Please enter a product in your cart");
    }

    public static void addQuantityToItem(String productName, int quantity, Map<String, Integer> items) {
        items.put(productName.toUpperCase(),items.get(productName.toUpperCase())+quantity);
    }

    public static void updateQuantityOnItem(String productName, int quantity, Map<String, Integer> items) {
        items.put(productName.toUpperCase(),quantity);
    }


    public static void printInstructions(){
        System.out.println("Press :");
        System.out.println("0  - To show available products");
        System.out.println("1  - To add a product to the cart");
        System.out.println("2  - To update a product");
        System.out.println("3  - To remove a product");
        System.out.println("4  - To print your cart");
        System.out.println("5  - To generate json file");
        System.out.println("99 - To quit");
        System.out.println("-----------------------------------");
        System.out.println("Enter your choice : ");

    }
    public static void printShopItems(Map<String, String> items){
        System.out.printf("-------------------------------------------%n");
        System.out.printf("|%-20s|%-20s|%n","PODUCT", "PRICE");
        System.out.printf("-------------------------------------------%n");
        items.entrySet().stream().forEach(item -> System.out.printf("|%-20s|%-20s|%n",item.getKey(), item.getValue()));
        System.out.printf("-------------------------------------------%n");

    }

    public static void printResultShopItems(Map<String, Integer> itemsCart, Map<String, String> items){
        System.out.printf("-------------------------------------------------------------------------------------%n");
        System.out.printf("|%-20s|%-20s|%-20s|%-20s|%n","PODUCT","QUANTITY","PRICE","TOTAL");
        System.out.printf("-------------------------------------------------------------------------------------%n");
        AtomicReference<Double> sumDoubleAtomic = new AtomicReference<>(0.0D);
        itemsCart.entrySet().stream().forEach(item -> {
            double roundedUpItemPriceTotal = BigDecimal.valueOf(Double.parseDouble(items.get(item.getKey())) * itemsCart.get(item.getKey())).setScale(2, RoundingMode.UP).doubleValue();

            sumDoubleAtomic.set(BigDecimal.valueOf(sumDoubleAtomic.get()+roundedUpItemPriceTotal).setScale(2, RoundingMode.UP).doubleValue());
            System.out.printf("|%-20s|%-20s|%-20s|%-20s|%n",item.getKey(), item.getValue(), items.get(item.getKey()), roundedUpItemPriceTotal);
            System.out.printf("-------------------------------------------------------------------------------------%n");
        });
        System.out.printf("|%-20s%43s%-20s|%n","TOTAL","",sumDoubleAtomic.get());
        System.out.printf("-------------------------------------------------------------------------------------%n");


    }

    public static CartSummry getCartSummry(Map<String, Integer> itemsCart, Map<String, String> items){
        AtomicReference<Double> sumDoubleAtomic = new AtomicReference<>(0.0D);
        List<CartItem> cartItemList = itemsCart.entrySet().stream().map(item -> {
            CartItem cartItem = new CartItem();
            cartItem.setNameProduct(item.getKey());
            cartItem.setQuantity(item.getValue());
            cartItem.setPrice(Double.parseDouble(items.get(item.getKey())));
            double roundedUpTotal = BigDecimal.valueOf(Double.parseDouble(items.get(item.getKey())) * itemsCart.get(item.getKey())).setScale(2, RoundingMode.UP).doubleValue();
            sumDoubleAtomic.set(sumDoubleAtomic.get()+roundedUpTotal);
            cartItem.setTotal(roundedUpTotal);
            return cartItem;
        }).collect(Collectors.toList());

        CartSummry cartSummry = new CartSummry();
        cartSummry.setItems(cartItemList);
        cartSummry.setTotal(sumDoubleAtomic.get());

        return cartSummry;

    }


}
