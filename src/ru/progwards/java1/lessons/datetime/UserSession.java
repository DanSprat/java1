package ru.progwards.java1.lessons.datetime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Random;

public class UserSession {
    private int sessionHandle;
    private String userName;
    private ZonedDateTime lastAccess;

    public int getSessionHandle() {
        return sessionHandle;
    }

    public String getUserName() {
        return userName;
    }

    public ZonedDateTime getLastAccess() {
        return lastAccess;
    }
    public void updateLastAccess(){
        lastAccess = Instant.now().atZone(ZoneId.of(ZoneId.systemDefault().getId()));
    }
    public UserSession(String userName){
        this.sessionHandle = new Random().nextInt();
        this.lastAccess = Instant.now().atZone(ZoneId.of(ZoneId.systemDefault().getId()));
        this.userName=userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSession)) return false;
        UserSession that = (UserSession) o;
        return sessionHandle == that.sessionHandle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionHandle);
    }

    @Override
    public String toString() {
        return "UserSession{ " +
                "sessionHandle = " + sessionHandle +
                ", userName = '" + userName + '\'' +
                ", lastAccess = " + lastAccess +
                 '}';
    }
}
