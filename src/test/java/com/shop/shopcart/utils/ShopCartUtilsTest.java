package com.shop.shopcart.utils;

import com.shop.shopcart.models.CartSummry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

class ShopCartUtilsTest {

    @Test
    void testGetPositiveIntegerOK() {
        assertThat(ShopCartUtils.getPositiveInteger("7")).isEqualTo(7);
    }

    @Test
    void testGetPositiveIntegerNotOK() {
        assertThat(ShopCartUtils.getPositiveInteger("test")).isEqualTo(-1);
    }

    @Test
    void testValidQuantity() {
        assertThat(ShopCartUtils.validQuantity(-2)).isFalse();
    }


    @Test
    void testValidateChoiceOK() {

        PrintStream mockedPrintStream = Mockito.mock(PrintStream.class);
        PrintStream old = System.out;
        System.setOut(mockedPrintStream);
        int choice = 2;
        ShopCartUtils.validateChoice(choice);
        Mockito.verify(mockedPrintStream).println("your choice is : " + choice);
        System.setOut(old);
    }

    @Test
    void testValidateChoiceKo() {
        PrintStream mockedPrintStream = Mockito.mock(PrintStream.class);
        PrintStream old = System.out;
        System.setOut(mockedPrintStream);
        int choice = -1;
        ShopCartUtils.validateChoice(choice);
        Mockito.verify(mockedPrintStream).println("your choice is invalid");
        System.setOut(old);
    }

    @Test
    void testAddItemToCartItems() {
        final Map<String, String> items = Map.ofEntries(Map.entry("PRODUCT1", "12.5"));
        final Map<String, Integer> cartItems = new HashMap<>();

        ShopCartUtils.addItemToCartItems("PRODUCT1", items, cartItems);

        assertThat(cartItems).containsOnlyKeys("PRODUCT1");

    }

    @Test
    void testDeleteItemFromCartItems() {
        final Map<String, String> items = Map.ofEntries(Map.entry("PRODUCT1", "12.5"));
        final Map<String, Integer> cartItems = new HashMap<>();

        ShopCartUtils.addItemToCartItems("PRODUCT1", items, cartItems);
        assertThat(cartItems).hasSize(1);

        ShopCartUtils.deleteItemFromCartItems("PRODUCT1", cartItems);
        assertThat(cartItems).isEmpty();

    }

    @Test
    void testAddQuantityToItem() {
        final Map<String, Integer> cartItems = new HashMap<>();
        cartItems.put("PRODUCT1",3);

        ShopCartUtils.addQuantityToItem("PRODUCT1", 2, cartItems);

        assertThat(cartItems.get("PRODUCT1")).isEqualTo(5);
    }

    @Test
    void testUpdateQuantityOnItem() {
        final Map<String, Integer> cartItems = new HashMap<>();
        cartItems.put("PRODUCT1",10);

        ShopCartUtils.updateQuantityOnItem("PRODUCT1", 3, cartItems);

        assertThat(cartItems.get("PRODUCT1")).isEqualTo(3);
    }

    @Test
    void testPrintInstructions() {
        ByteArrayOutputStream systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));

        ShopCartUtils.printInstructions();

        then(systemOutContent.toString()).contains("1  - To add a product to the cart");

        System.setOut(System.out);
    }

    @Test
    void testPrintShopItems() {
        ByteArrayOutputStream systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));

        final Map<String, String> items = Map.ofEntries(Map.entry("PRODUCT1", "12.5"), Map.entry("PRODUCT2", "4.5"));

        ShopCartUtils.printShopItems(items);

        then(systemOutContent.toString()).contains("|PRODUCT2            |4.5                 |");
        then(systemOutContent.toString()).contains("|PRODUCT1            |12.5                |");

        System.setOut(System.out);
    }

    @Test
    void testPrintResultShopItems() {
        ByteArrayOutputStream systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));

        final Map<String, Integer> itemsCart = Map.ofEntries(Map.entry("PRODUCT1", 2), Map.entry("PRODUCT2", 3));
        final Map<String, String> items = Map.ofEntries(Map.entry("PRODUCT1", "10.5"), Map.entry("PRODUCT2", "12.13"));

        ShopCartUtils.printResultShopItems(itemsCart, items);

        then(systemOutContent.toString()).contains("|PRODUCT1            |2                   |10.5                |21.0                |");
        then(systemOutContent.toString()).contains("|PRODUCT2            |3                   |12.13               |36.39               |");
        then(systemOutContent.toString()).contains("|TOTAL                                                          57.39               |");

        System.setOut(System.out);
    }

    @Test
    void testGetCartSummry() {
        final Map<String, Integer> itemsCart = Map.ofEntries(Map.entry("PRODUCT1", 2), Map.entry("PRODUCT2", 5));
        final Map<String, String> items = Map.ofEntries(Map.entry("PRODUCT1", "12.5"), Map.entry("PRODUCT2", "4.5"));

        final CartSummry result = ShopCartUtils.getCartSummry(itemsCart, items);

        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getTotal()).isEqualTo(47.5d);

    }
}
