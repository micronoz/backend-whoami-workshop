package com.example.whoami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class GameController {
    @Autowired
    private UserRepository uRep;
    @Autowired
    private MessageRepository mRep;
    public static boolean isGameOn;
    public static boolean readyCheck;

    @PostMapping("/api/v1/game/status")
    @ResponseBody
    public GameState getStatus(@RequestParam("id") String id) {
        User myUser = uRep.findById(id);
        if (myUser == null) {
            throw new UnauthorizedException();
        }
        GameState curState = new GameState();

        if (!isGameOn && !readyCheck) {
            throw new IllegalGameStateException("Game has not started yet.");
        }

        else if (readyCheck && !isGameOn) {
            curState.needsReady = true;
            return curState;
        }

        else {
            curState.messages = new ArrayList<Message>();
            mRep.findBySenderIdOrReceiverId(id).forEach(curState.messages::add);
            return curState;
        }
    }

    @PostMapping("/api/v1/game/ready")
    public void getReady(@RequestParam("id") String id) {
        User u = uRep.findById(id);
        if (u != null) {
            u.resetState();
            u.setReady();
        }
    }
}