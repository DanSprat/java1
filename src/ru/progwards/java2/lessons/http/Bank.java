package ru.progwards.java2.lessons.http;

import ru.progwards.java2.lessons.http.Exceptions.NotEnoughMoneyException;
import ru.progwards.java2.lessons.http.model.Account;
import ru.progwards.java2.lessons.http.service.AccountService;


import java.util.concurrent.ConcurrentHashMap;

public class Bank implements AccountService {

    private final ConcurrentHashMap <String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public double balance(Account account) {
        return account.getAmount();
    }

    @Override
    public void deposit(Account account, double amount) {
        synchronized (account){
            account.setAmount(account.getAmount() + amount);
        }

    }

    @Override
    public void withdraw(Account account, double amount) throws NotEnoughMoneyException {
        synchronized (account){
            double newAmount = account.getAmount() - amount;
            if (newAmount < 0)
                throw new NotEnoughMoneyException(account.getId());
            account.setAmount(newAmount);
        }
    }

    @Override
    public void transfer(Account from, Account to, double amount) throws NotEnoughMoneyException {
        synchronized (from){
            withdraw(from,amount);
        }
        synchronized (to){
            deposit(to,amount);
        }

    }

    public Account getAccount(String id){
        return accounts.get(id);
    }
    public void addAccount(Account account){
        accounts.put(account.getId(),account);
    }
}
