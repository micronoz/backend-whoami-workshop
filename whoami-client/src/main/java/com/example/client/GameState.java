package com.example.client;

import java.util.List;

public class GameState {
    public boolean needsReady;
    public boolean gameInProgress;
    public Message[] messages;
    public boolean yourTurn;
    public String condition;
    public boolean isAsking;
}