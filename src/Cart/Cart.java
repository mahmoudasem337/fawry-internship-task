package Cart;

import Customer.Customer;
import Interfaces.IExpirable;
import Interfaces.IShippable;
import Products.Product;
import Products.ShippableProduct;
import Shipping.ShippingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;
    private Customer customer;

    public Cart(Map<Product, Integer> items, Customer customer) {
        this.items = items;
        this.customer = customer;
    }

    public Cart() {

    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("please insert valid quantity number (bigger than 0)");
        }
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("sorry, no enough stock");
        }
        product.reduceQuantity(quantity);
        items.put(product, quantity);
    }

    public Map<Product, Integer> getItems() {
        return new HashMap<>(items);
    }

    public double calculateSubTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            //total price : for each product = (product price)*(product quantity)
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public double calculateShippingFee() {
        return 100;
    }

    public void checkout() {
        //error if : cart is empty, product out of stock, product is expired, user haven't enough balance
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot checkout: Cart is empty");
        }
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (quantity > product.getQuantity()) {
                throw new IllegalStateException("out of stock: " + product.getName());
            }
            if (product instanceof IExpirable && ((IExpirable) product).isExpired()) {
                throw new IllegalStateException("Product expired: " + product.getName());
            }

        }

        double subtotal = calculateSubTotal();
        double shippingFee = calculateShippingFee();
        double total = subtotal + shippingFee;
        double currenctbalance = customer.checkBalance(total);
        System.out.println("Sub Total : " + subtotal);
        System.out.println("Shipping Fee : " + shippingFee);
        System.out.println("Total : " + total);
        System.out.println("Current balance : " + currenctbalance);
    }

    public List<ShippableProduct> shipping() {
        List<ShippableProduct> shippableItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product instanceof IShippable) {
                for (int i = 0; i < quantity; i++) {
                    shippableItems.add((ShippableProduct) product);
                }
            }
        }
        return shippableItems;
    }
}