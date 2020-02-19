package com.example.whoami;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String userName;
    private String gamePartner;
    private boolean isReady;
    private boolean isTurn;
    private boolean isAsking;

    protected User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return String.format("User[id='%s', userName='%s']", id, userName);
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPartner() {
        return gamePartner;
    }

    public Boolean isAsking() {
        return isAsking;
    }

    public void setPartner(String gamePartner) {
        this.gamePartner = gamePartner;
    }

    public void setAsking() {
        isAsking = true;
    }

    public void setReady() {
        isReady = true;
    }

    public Boolean isTurn() {
        return isTurn;
    }

    public void setTurn() {
        isTurn = true;
    }

    public void resetState() {
        gamePartner = null;
        isAsking = false;
        isReady = false;
        isTurn = false;
    }
}