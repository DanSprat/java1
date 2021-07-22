package ru.progwards.java2.lessons.http.Exceptions;

import java.io.IOException;

public class NotEnoughMoneyException extends IOException {
    public NotEnoughMoneyException(String account){
        super("There is not enough money on the account " + account);
    }
}
