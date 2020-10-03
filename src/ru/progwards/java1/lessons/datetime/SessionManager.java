package ru.progwards.java1.lessons.datetime;

import javax.swing.text.html.parser.Entity;
import java.security.KeyStore;
import java.time.ZonedDateTime;
import java.util.*;

public class SessionManager {
    private HashMap<Integer,UserSession> sessions;
    private int sessionValid;

    public SessionManager(int sessionValid){
        this.sessionValid=sessionValid;
        sessions= new HashMap<>();
    }
    public void add(UserSession userSession){
        sessions.put(userSession.getSessionHandle(),userSession);
    }
    public UserSession find(String userName){
       for (var x:sessions.entrySet()){
           if(x.getValue().getUserName()==userName && x.getValue().getLastAccess().plusSeconds(sessionValid).isAfter(ZonedDateTime.now())){
               return x.getValue();
           }
       }
       return null;
    }

    public UserSession get(int sessionHandle){
        if (sessions.get(sessionHandle)==null)
            return null;
        if(sessions.get(sessionHandle).getLastAccess().plusSeconds(sessionValid).isAfter(ZonedDateTime.now()))
            return sessions.get(sessionHandle);
        return null;
    }
    public void delete(int sessionHandle){
        sessions.remove(sessionHandle);
    }
    public void deleteExpired(){
         Set<Map.Entry<Integer,UserSession>> entries = sessions.entrySet();
         var it = entries.iterator();
         while(it.hasNext()){
             var next = it.next();
             if (next.getValue().getLastAccess().plusSeconds(sessionValid).isBefore(ZonedDateTime.now())){
                // sessions.remove(next.getKey());
                 it.remove();
             }
         }

        /*for (var x : sessions.entrySet()){
            if (x.getValue().getLastAccess().plusSeconds(sessionValid).isBefore(ZonedDateTime.now()))
                sessions.remove(x.getKey());
        }*/
    }

    public static void main(String[] args) throws Exception {
        SessionManager sessionManager = new SessionManager(5);
        if(sessionManager.find("DanSprat")==null)
            System.out.println("Сессия не существует или истелка");
        UserSession session = new UserSession("DanSprat");
        sessionManager.add(session);
        System.out.println(sessionManager.get(session.getSessionHandle()));
        Thread.sleep(1000);
        System.out.println(sessionManager.get(session.getSessionHandle()));
        Thread.sleep(1000);
        System.out.println(sessionManager.get(session.getSessionHandle()));
        Thread.sleep(1000);
        System.out.println(sessionManager.get(session.getSessionHandle()));
        Thread.sleep(7*1000);
        if (sessionManager.get(session.getSessionHandle())==null)
           System.out.println("Сессия не существует или истелка");
        UserSession session1 = new UserSession("DanSprat");
        sessionManager.add(session1);
        Thread.sleep(2*1500);
        UserSession session2 = new UserSession("DanielSprat");
        sessionManager.add(session2);
        Thread.sleep(2*1500);
        sessionManager.deleteExpired();
        if (sessionManager.get(session1.getSessionHandle()) == null)
            System.out.println("Сессия не существует или истелка");
        System.out.println(sessionManager.get(session2.getSessionHandle()));

    }


}
