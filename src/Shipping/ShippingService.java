package Shipping;

import Cart.Cart;
import Interfaces.Iship;
import Products.ShippableProduct;
import java.util.List;

public class ShippingService {
    public void shipItems(List<ShippableProduct> items) {
        System.out.println("Shipping the following items:");
        for (ShippableProduct item : items) {
            System.out.printf("  - %s (Weight: %.2f kg)%n", item.getName(), item.getWeight());
        }
    }
}
