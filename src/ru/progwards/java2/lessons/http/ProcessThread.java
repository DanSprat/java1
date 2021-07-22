package ru.progwards.java2.lessons.http;


import ru.progwards.java2.lessons.http.Exceptions.NotEnoughMoneyException;
import ru.progwards.java2.lessons.http.model.Account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ProcessThread extends Thread{
    String error404 = "<!doctype html>\n" +
            "<html lang=\"ru\">\n" +
            "<head>\n" +
            "  <meta charset=\"utf-8\" />\n" +
            "  <title></title>\n" +
            "  <link rel=\"stylesheet\" href=\"style.css\" />\n" +
            "</head>\n" +
            "<body>\n" +
            " Resource Not found    \n" +
            "</body>\n" +
            "</html>";

    String error400 =  "<!doctype html>\n" +
            "<html lang=\"ru\">\n" +
            "<head>\n" +
            "  <meta charset=\"utf-8\" />\n" +
            "  <title>Request Error</title>\n" +
            "  <link rel=\"stylesheet\" href=\"style.css\" />\n" +
            "</head>\n" +
            "<body>\n" +
            " Request Error   \n" +
            "</body>\n" +
            "</html>";
    Socket incoming;
    Bank bank;

    public ProcessThread(Socket incoming,Bank bank){
        this.bank = bank;
        this.incoming = incoming;
    }


    private boolean splitParams(int size,String [] params,String [] resourceParams,String text,String response,PrintWriter printWriter, String [] values){
        String[] keyValue;
        String key= "";
        String value = "";
        for(int i = 0;i<size;i++){
            keyValue = params[i].split("=");
            if (keyValue.length != 2){
                text = makeHTML("Bad Request 405","Bad Request");
                response += "400 Bad Request\nContent-Type: text/html; charset=utf-8\n" +
                        "Content-Length: "+text.length()+"\n\n"+ text;
                printWriter.println(response);
                return true;
            }
            key = keyValue[0];
            value =keyValue[1];
            if (!key.equals(resourceParams[i]) ){
                text = makeHTML("Bad Request 405","Bad Request");
                response += "400 Bad Request\nContent-Type: text/html; charset=utf-8\n" +
                        "Content-Length: "+text.length()+"\n\n"+ text;
                printWriter.println(response);
                return true;
            }
            values[i] = value;
        }
        return false;
    }
    private String makeHTML(String head,String body){
        return "<!doctype html>\n" +
                "<html lang=\"ru\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\" />\n" +
                "  <title>"+head+ "</title>\n" +
                "  <link rel=\"stylesheet\" href=\"style.css\" />\n" +
                "</head>\n" +
                "<body>\n" +
                 body + "\n" +
                "</body>\n" +
                "</html>";
    }
    @Override
    public void run() {
        try( InputStream is = incoming.getInputStream();
             OutputStream os = incoming.getOutputStream();
             PrintWriter printWriter = new PrintWriter(os)){
            String text="";
            String response = "HTTP/1.1 ";
            try(Scanner scanner = new Scanner(is)){
                String newStr = scanner.nextLine();
                String [] params = newStr.split("/");
                if (params.length != 3 || !params[0].trim().equalsIgnoreCase("get")){
                    response += "404 Not Found\nContent-Type: text/html; charset=utf-8\n" +
                            "Content-Length: "+error400.length()+"\n\n"+ error400;
                }
                String request = params[1].trim().toLowerCase();
                newStr = request.substring(params[1].length()-4);
                if(!newStr.equalsIgnoreCase("http")){
                    response += "400 Bad Request\nContent-Type: text/html; charset=utf-8\n" +
                            "Content-Length: "+error400.length()+"\n\n"+ error400;
                }
                request =  request.substring(0,request.length()-4);
                String resAndParams [] = request.split("\\?");
                String resource = resAndParams[0].trim();
                params = resAndParams[1].trim().split("&");
                switch (resource){
                    case "transfer":
                    {
                        String [] values = new String [3];
                        String [] resourceParams = {"from","to","amount"};
                        if (params.length != 3){
                            text = makeHTML("Bad Request 405","Bad Request");
                            response += "400 Bad Request\nContent-Type: text/html; charset=utf-8\n" +
                                    "Content-Length: "+text.length()+"\n\n"+ text;
                            printWriter.println(response);
                            printWriter.flush();
                            return;
                        }
                        if(splitParams(3,params,resourceParams,text,response,printWriter,values))
                            return;
                        Account from = bank.getAccount(values[0]);
                        if (from == null){
                            text = makeHTML("Account not found","The sender's account was not found");
                            response += "405 Method Not Allowed\nContent-Type: text/html; charset=utf-8\n" +
                                    "Content-Length: "+text.length()+"\n\n"+ text;
                            printWriter.println(response);
                            printWriter.flush();
                            return;
                        }
                        Account to = bank.getAccount(values[1]);
                        if (to == null){
                            text = makeHTML("Account not found","The recipient's account was not found");
                            response += "405 Method Not Allowed\nContent-Type: text/html; charset=utf-8\n" +
                                    "Content-Length: "+text.length()+"\n\n"+ text;
                            printWriter.println(response);
                            printWriter.flush();
                            return;
                        }
                        try {
                            bank.transfer(from,to,Double.parseDouble(values[2]));
                            text = makeHTML("Successfully",values[2] + " was transferred from "+from.getId()+" to "+to.getId() );
                        }catch (NotEnoughMoneyException exception){
                            text= makeHTML("The operation failed",exception.getMessage());
                        }
                        response+= "200 OK\nContent-Type: text/html; charset=utf-8\n" +  "Content-Length: "+text.length()+ "\n\n"+ text;
                        printWriter.println(response);
                        printWriter.flush();
                        break;
                    }
                    case "deposit":{
                        String [] values = new String [2];
                        String [] resourceParams = {"account","amount"};
                        if (params.length != 2){
                            text = makeHTML("Bad Request 405","Bad Request");
                            response += "400 Bad Request\nContent-Type: text/html; charset=utf-8\n" +
                                    "Content-Length: "+text.length()+"\n\n"+ text;
                            printWriter.println(response);
                            printWriter.flush();
                            return;
                        }
                        if(splitParams(2,params,resourceParams,text,response,printWriter,values))
                            return;
                        Account account = bank.getAccount(values[0]);
                        if (account == null){
                            text = makeHTML("Account not found","The account was not found");
                            response += "405 Method Not Allowed\nContent-Type: text/html; charset=utf-8\n" +
                                    "Content-Length: "+text.length()+"\n\n"+ text;
                            printWriter.println(response);
                            printWriter.flush();
                            return;
                        }

                        bank.deposit(account,Double.parseDouble(values[1]));
                        text = makeHTML("Successfully","The account "+account.getId()+" was deposited by "+ values[1]);
                        response+= "200 OK\nContent-Type: text/html; charset=utf-8\n" +  "Content-Length: "+text.length()+ "\n\n"+ text;
                        printWriter.println(response);
                        printWriter.flush();
                        break;

                    }
                    case "withdraw": {
                        String [] values = new String [2];
                        String [] resourceParams = {"account","amount"};
                        if (params.length != 2){
                            text = makeHTML("Bad Request 405","Bad Request");
                            response += "400 Bad Request\nContent-Type: text/html; charset=utf-8\n" +
                                    "Content-Length: "+text.length()+"\n\n"+ text;
                            printWriter.println(response);
                            printWriter.flush();
                            return;
                        }
                        if(splitParams(2,params,resourceParams,text,response,printWriter,values))
                            return;
                        Account account = bank.getAccount(values[0]);
                        if (account == null){
                            text = makeHTML("Account not found","The account was not found");
                            response += "405 Method Not Allowed\nContent-Type: text/html; charset=utf-8\n" +
                                    "Content-Length: "+text.length()+"\n\n"+ text;
                            printWriter.println(response);
                            printWriter.flush();
                            return;
                        }
                        try {
                            bank.withdraw(account,Double.parseDouble(values[1]));
                            text = makeHTML("Successful operation",values[1]+"was successfully withdrawn from account "+account.getId());
                        } catch (NotEnoughMoneyException ex){
                            text = makeHTML("The operation failed",ex.getMessage());
                        }
                        response += "200 OK\nContent-Type: text/html; charset=utf-8\n" +
                                "Content-Length: "+text.length()+"\n\n"+ text;
                        printWriter.println(response);
                        printWriter.flush();
                        break;
                    }

                    case  "balance":{
                        String [] values = new String [1];
                        String [] resourceParams = {"account"};
                        if (params.length != 1){
                            text = makeHTML("Bad Request 405","Bad Request");
                            response += "400 Bad Request\nContent-Type: text/html; charset=utf-8\n" +
                                    "Content-Length: "+text.length()+"\n\n"+ text;
                            printWriter.println(response);
                            printWriter.flush();
                            return;
                        }
                        if(splitParams(1,params,resourceParams,text,response,printWriter,values))
                            return;
                        Account account = bank.getAccount(values[0]);
                        if (account == null){
                            text = makeHTML("Account not found","The recipient's account was not found");
                            response += "405 Method Not Allowed\nContent-Type: text/html; charset=utf-8\n" +
                                    "Content-Length: "+text.length()+"\n\n"+ text;
                            printWriter.println(response);
                            printWriter.flush();
                            return;
                        }
                        double balance = bank.balance(account);
                        text = makeHTML("Successfully","The account "+account.getId()+" balance is "+balance);
                        response+= "200 OK\nContent-Type: text/html; charset=utf-8\n" +  "Content-Length: "+text.length()+ "\n\n"+ text;
                        printWriter.println(response);
                        printWriter.flush();
                        break;
                    }
                    default:{
                        response += "404 Not Found\nContent-Type: text/html; charset=utf-8\n" +
                                "Content-Length: "+error404.length()+"\n\n"+ error404;
                        printWriter.println(response);
                        printWriter.flush();
                    }
                }
            }


        } catch (IOException exception){
            exception.printStackTrace();
        }

    }
}
