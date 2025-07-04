package Interfaces;

import Products.ShippableProduct;

import java.util.List;

public interface Iship {
    public void getWeight(List<ShippableProduct> products);
    public void getName(List<ShippableProduct> products);
}
