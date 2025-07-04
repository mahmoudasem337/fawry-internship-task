package Interfaces;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IExpirable {
    public LocalDate getExpirationTime();
    public boolean isExpired();
}
