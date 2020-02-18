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

    private String question;
    private int answer;
    private String senderId;
    private String receiverId;

    protected Message() {

    }

    public Message(String question) {
        this.question = question;
    }

    public Message(int answer) {
        this.answer = answer;
    }

    public String getContent() {
        return this.question;
    }

    public int getAnswer() {
        return this.answer;
    }

    public String getSender() {
        return senderId;
    }

    public String getReceiver() {
        return receiverId;
    }

    @Override
    public String toString() {
        return "";
    }

}