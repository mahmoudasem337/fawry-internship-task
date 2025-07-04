package Products;

import Interfaces.IExpirable;

import java.time.LocalDate;

public class ExpirableProduct extends Product implements IExpirable {
    private LocalDate expirationDate;
    private boolean isExpired;

    public ExpirableProduct(String name, double price,int quantity, LocalDate expirationDate, boolean isExpired) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        this.isExpired = false;
    }

    @Override
    public LocalDate getExpirationTime() {
        return expirationDate;
    }
    @Override
    public boolean isExpired(){
        return isExpired;
    }
}