package com.example.whoami;

import java.util.List;

public class GameState {
    public boolean needsReady;
    public boolean gameInProgress;
    public boolean gameEnded;
    public String pair;
    public List<Message> messages;
    public boolean yourTurn;
    public String condition;
    public boolean isAsking;
}