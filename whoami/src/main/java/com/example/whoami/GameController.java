package com.example.whoami;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @Autowired
    private UserRepository uRep;
    @Autowired
    private MessageRepository mRep;
    public static boolean isGameOn;
    public static boolean readyCheck;
    public static boolean endGame;

    @PostMapping("/api/v1/game/status")
    @ResponseBody
    public GameState getStatus(@RequestBody IncomingUserId id) {
        User myUser = uRep.findById(id.id);
        if (myUser == null) {
            throw new UnauthorizedException();
        }
        GameState curState = new GameState();
        curState.gameInProgress = isGameOn;

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
            mRep.findBySenderIdOrReceiverId(id.id).forEach(curState.messages::add);
            curState.yourTurn = myUser.isTurn();
            curState.isAsking = myUser.isAsking();

            if (endGame) {
                curState.pair = String.format("Your partner was: '%s'", myUser.getPartner().getUserName());
                curState.gameEnded = true;
            }

            return curState;
        }

        else {
            throw new UnauthorizedException();
        }
    }

    @PostMapping("/api/v1/game/send")
    public void sendMessage(@RequestBody IncomingMessage m) {
        if (!isGameOn) {
            throw new IllegalGameStateException("Game has not started yet.");
        }
        User myUser = uRep.findById(m.id);
        if (myUser == null)
            throw new UnauthorizedException();
        if (!myUser.isTurn())
            throw new IllegalGameStateException("It is not your turn yet.");
        User yourUser = myUser.getPartner();
        myUser.reverseTurn();
        yourUser.reverseTurn();
        uRep.save(myUser);
        uRep.save(yourUser);
        mRep.save(new Message(m.id, yourUser.getId(), m.message, myUser.isAsking()));
    }

    @PostMapping("/api/v1/game/ready")
    public void getReady(@RequestBody IncomingUserId id) {
        User u = uRep.findById(id.id);

        if (u != null) {
            if (isGameOn && readyCheck) {
                throw new IllegalGameStateException("The game has already started, wait for next game.");
            }
            u.resetState();
            u.setReady();
        }
        uRep.save(u);
    }
}