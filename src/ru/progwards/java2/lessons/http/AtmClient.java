package ru.progwards.java2.lessons.http;

import ru.progwards.java2.lessons.http.Exceptions.NotEnoughMoneyException;
import ru.progwards.java2.lessons.http.model.Account;
import ru.progwards.java2.lessons.http.service.AccountService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class AtmClient implements AccountService {

    private ATM Atm;
    final private String GET = "GET";
    final private String HTTP = "HTTP/1.1";

    public AtmClient(ATM Atm) {
        this.Atm = Atm;
    }

    @Override
    public double balance(Account account) {
        try (Socket socket = new Socket("localhost",40)){
            OutputStream os = socket.getOutputStream();
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(os);
            String request = GET +"/balance?"+ "account="+account.getId()+HTTP+"\nhostname: localhost\n\n";
            printWriter.println(request);
            printWriter.flush();
            socket.shutdownOutput();
            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deposit(Account account, double amount) {
        try(Socket socket = new Socket("localhost",40)) {
            OutputStream os = socket.getOutputStream();
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(os);
            String request = GET +"/deposit?"+ "account="+account.getId()+"&amount="+amount+HTTP+"\nhostname: localhost\n\n";
            printWriter.println(request);
            printWriter.flush();
            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void withdraw(Account account, double amount)  {
        try(Socket socket = new Socket("localhost",40)) {
            OutputStream os = socket.getOutputStream();
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(os);
            String request = GET +"/withdraw?"+ "account="+account.getId()+"&amount="+amount+HTTP+"\nhostname: localhost\n\n";
            printWriter.println(request);
            printWriter.flush();
            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        try(Socket socket = new Socket("localhost",40)) {
            OutputStream os = socket.getOutputStream();
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(os);
            String request = GET +"/transfer?"+ "from="+from.getId()+"&to="+to.getId()+"&amount="+amount+HTTP+"\nhostname: localhost\n\n";
            printWriter.println(request);
            printWriter.flush();
            socket.shutdownOutput();
            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void main(String[] args)  {
        Bank someBank = new Bank();
        ATM someAtm = new ATM(someBank); // Привязываем банк к банкомату
        someAtm.start(); // "Запускаем" банкомат
        ArrayList<Account> accounts = new ArrayList<>();
        for (int i =0;i<2;i++){
            Account acc = new Account();
            String id = UUID.randomUUID().toString();
            acc.setId(id);
            acc.setPin(1000+i);
            acc.setHolder("Account_"+i);
            acc.setDate(new Date(System.currentTimeMillis()+365*24*3600*1000));
            acc.setAmount(0);
            someBank.addAccount(acc);
            accounts.add(acc);
        }
        AtmClient atmClient = new AtmClient(someAtm); // Клиент банкомата
        atmClient.balance(accounts.get(0));
        atmClient.withdraw(accounts.get(0),1);
        atmClient.deposit(accounts.get(0),1000);
        atmClient.deposit(accounts.get(1),2000);
        atmClient.transfer(accounts.get(1),accounts.get(0),1000);
        atmClient.balance(accounts.get(0));
        atmClient.transfer(accounts.get(1),accounts.get(0),2000);
    }
}
