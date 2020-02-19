package com.example.whoami;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;
    private String senderId;
    private String receiverId;
    private boolean isQuestion;

    protected Message() {

    }

    public Message(String senderId, String receiverId, String message, boolean isQuestion) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.isQuestion = isQuestion;
    }

    public String getContent() {
        return this.message;
    }

    public String getSender() {
        return senderId;
    }

    public String getReceiver() {
        return receiverId;
    }

    public Boolean isQuestion() {
        return isQuestion;
    }

    public void makeQuestion() {
        this.isQuestion = true;
    }

    @Override
    public String toString() {
        return this.message;
    }

}