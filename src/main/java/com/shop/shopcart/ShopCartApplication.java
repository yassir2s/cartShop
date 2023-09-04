package com.shop.shopcart;


import com.shop.shopcart.services.ShopService;
import com.shop.shopcart.utils.ShopCartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class ShopCartApplication {

    @Autowired
    private ShopService shopService;


    @Bean
    CommandLineRunner commandLineRunner() {
        return args ->  run();
    }


    public static void main(String[] args) {
        SpringApplication.run(ShopCartApplication.class, args);
    }

    private void run(){
        int choice = 0;
        while(true){
            shopService.printInstructions();
            choice = ShopCartUtils.getPositiveInteger(shopService.getScanner().next());
            ShopCartUtils.validateChoice(choice);
            switch (choice){
                case 0:
                    shopService.printShopItems();
                    break;
                case 1:
                    shopService.addProduct();
                    break;
                case 2:
                    shopService.updateProduct();
                    break;
                case 3:
                    shopService.deleteProduct();
                    break;
                case 4:
                    shopService.printResultShopItems();
                    break;
                case 5:
                    shopService.generateJsonFile();
                    break;
                case 99:
                    System.out.println("Thank you for your purchase");
                    System.exit(0);
            }
        }
    }




}
