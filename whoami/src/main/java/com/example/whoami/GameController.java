package com.example.whoami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    @Async
    public GameState getStatus(@RequestParam("id") String id) {
        User myUser = uRep.findById(id);
        if (myUser == null) {
            throw new UnauthorizedException();
        }
        GameState curState = new GameState();

        if (!readyCheck) {
            throw new IllegalGameStateException("Game has not started yet.");
        }

        else if (readyCheck && !isGameOn) {
            curState.needsReady = !myUser.isReady();
            curState.condition = "Game is about to start, please get ready.";
            return curState;
        }

        else if (myUser.isReady()) {
            curState.messages = new ArrayList<Message>();
            mRep.findBySenderIdOrReceiverId(id).forEach(curState.messages::add);
            curState.yourTurn = myUser.isTurn();
            return curState;
        }

        else {
            throw new UnauthorizedException();
        }
    }

    @PostMapping("/api/v1/game/send")
    public void sendMessage(@RequestParam("id") String id, @RequestParam("message") String message) {
        if (!isGameOn) {
            throw new IllegalGameStateException("Game has not started yet.");
        }
        User myUser = uRep.findById(id);
        if (myUser == null)
            throw new UnauthorizedException();
        User yourUser = myUser.getPartner();
        myUser.reverseTurn();
        yourUser.resetState();
        mRep.save(new Message(id, yourUser.getId(), message));
    }

    @PostMapping("/api/v1/game/ready")
    public void getReady(@RequestParam("id") String id) {
        User u = uRep.findById(id);

        if (u != null) {
            if (isGameOn && readyCheck) {
                throw new IllegalGameStateException("The game has already started, wait for next game.");
            }
            u.resetState();
            u.setReady();
        }
    }
}