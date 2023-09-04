package com.shop.shopcart.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shopcart.models.CartSummry;
import com.shop.shopcart.models.ProductsCatalogue;
import com.shop.shopcart.utils.ShopCartUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
@Getter
public class ShopService {

    @Value("${shop.pathFile}")
    private String pathFile;

    private Scanner scanner = new Scanner(System.in);

    private Map<String, Integer> items = new HashMap<>();


    private final ProductsCatalogue productsCatalogue;

    public ShopService(ProductsCatalogue productsCatalogue) {
        this.productsCatalogue = productsCatalogue;
    }


    public void addProduct(){
        System.out.println("Adding product");
        String productName;
        int quantity;
        while(true){
            System.out.println("enter product name :");
            productName = scanner.next();
            boolean productAdded = ShopCartUtils.addItemToCartItems(productName, productsCatalogue.getProducts(), items);
            if(productAdded){
                break;
            }
        }
        while(true){
            System.out.println("Enter the quantity : " );
            quantity = ShopCartUtils.getPositiveInteger(scanner.next());
            if(ShopCartUtils.validQuantity(quantity)){
                ShopCartUtils.addQuantityToItem(productName, quantity, items);
                break;
            }else{
                System.out.println("please write strictly positive integer !");
            }
        }

        ShopCartUtils.printResultShopItems(items,productsCatalogue.getProducts());
    }

    public void updateProduct(){
        if(items.isEmpty()){
            System.out.println("The cart is empty!");
            return;
        }
        System.out.println("updating product");
        String productName;
        int quantity;
        while(true){
            System.out.println("enter product name :");
            productName = scanner.next();
            if(items.containsKey(productName.toUpperCase())){
                break;
            }
            System.out.println("Please enter a product in the cart !");
        }
        while(true){
            System.out.println("Enter the new quantity : " );
            quantity = ShopCartUtils.getPositiveInteger(scanner.next());
            if(ShopCartUtils.validQuantity(quantity)){
                ShopCartUtils.updateQuantityOnItem(productName, quantity, items);
                break;
            }else{
                System.out.println("please write strictly positive integer !");
            }
        }

        ShopCartUtils.printResultShopItems(items,productsCatalogue.getProducts());
    }

    public void deleteProduct(){
        if(items.isEmpty()){
            System.out.println("The cart is empty!");
            return;
        }
        System.out.println("Deleting product");
        String productName;
        while(true){
            System.out.println("enter product name :");
            productName = scanner.next();
            int itemsSize = items.size();
            ShopCartUtils.deleteItemFromCartItems(productName, items);
            if(items.size() < itemsSize){
                break;
            }
        }
        ShopCartUtils.printResultShopItems(items,productsCatalogue.getProducts());
    }


    public void printInstructions(){
        ShopCartUtils.printInstructions();
    }
    public void printShopItems(){
        ShopCartUtils.printShopItems(productsCatalogue.getProducts());
    }
    public void printResultShopItems(){
        ShopCartUtils.printResultShopItems(items,productsCatalogue.getProducts());
    }

    public void generateJsonFile(){
        CartSummry cartSummry = ShopCartUtils.getCartSummry(items, productsCatalogue.getProducts());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println("Generating file : " + pathFile);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(pathFile), cartSummry);
        } catch (IOException e) {
            System.out.println("Error generating file !!");
            throw new RuntimeException(e);
        }


    }
}
