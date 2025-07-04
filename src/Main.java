import Cart.Cart;
import Customer.Customer;
import Products.ExpirableProduct;
import Products.Product;
import Products.ShippableProduct;
import Shipping.ShippingService;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Barca Shop ---\n");

        ShippableProduct Laptop = new ShippableProduct("Laptop", 2000.0, 5, 2.5);
        ExpirableProduct Milk = new ExpirableProduct("Milk", 10.0, 20, LocalDate.now().plusDays(5), false);
        ExpirableProduct Cheese = new ExpirableProduct("Cheese", 15.0, 3, LocalDate.now().minusDays(2), true);
        
        Customer pedri = new Customer("pedri", "password123", 3000.0);
        Customer gavi = new Customer("gavi", "password123", 10.0);

        Map<Product, Integer> items = new HashMap<>();
        Cart cart = new Cart(items, pedri);
        try {
            cart.addProduct(Laptop, 1);
            cart.addProduct(Milk, 3);
            System.out.println("Added products to cart successfully.");
        } catch (Exception e) {
            throw new IllegalStateException("Cannot add products to the cart !");
        }

        try {
            double subtotal = cart.calculateSubTotal();
            double shippingFee = cart.calculateShippingFee();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot calculate subtotal !");
        }

        try {
            cart.checkout();
            System.out.println("Checkout successful.");
        } catch (Exception e) {
            throw new IllegalStateException("Cannot checkout !");
        }

        try {
            Cart cart2 = new Cart(new HashMap<>(), gavi);
            cart2.addProduct(Milk, 5);
            cart2.checkout();
            throw new IllegalStateException("Cannot checkout !");
        } catch (Exception e) {
            System.out.println("Correctly caught insufficient balance for cutomer : " +gavi.getUsername());
        }


        try {
            Cart stockCart = new Cart(new HashMap<>(), pedri);
            stockCart.addProduct(Milk, 40); // more than available
            stockCart.checkout();
            throw new IllegalStateException("Cannot checkout !");
        } catch (Exception e) {
            System.out.println("Correctly caught out-of-stock");
        }

        try {
            List<ShippableProduct> shippables = cart.shipping();
            ShippingService shippingService = new ShippingService();
            shippingService.shipItems(shippables);
            System.out.println("Shipping products successfully.");
        } catch (Exception e) {
            throw new IllegalStateException("Cannot ship products");
        }

    }
}