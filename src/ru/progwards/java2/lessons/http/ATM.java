package ru.progwards.java2.lessons.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ATM extends Thread {

    private Bank bank; // Банк к которому привязан банкомат

    public ATM(Bank bank){
        this.bank = bank;
    }


    public Bank getBank() {
        return bank;
    }

    @Override
    public void run() {
        if (bank == null){
            System.out.println("No connection to Bank");
            return;
        }
        try(ServerSocket server = new ServerSocket(40)) {
            while (true){
                Socket socket = server.accept();
                new Thread(new ProcessThread(socket,bank)).start();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {

    }
}
