package com.example.whoami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @Autowired
    private UserRepository uRep;
    @Autowired
    private MessageRepository mRep;
    public static Boolean isGameOn;

    @PostMapping("/api/v1/game/status")
    @ResponseBody
    public Boolean getStatus(@RequestParam("id") String id) {
        return isGameOn;
    }

    // @PostMapping("/api/v1/game/messages")
    // public Boolean
}