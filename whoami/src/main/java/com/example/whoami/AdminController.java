package com.example.whoami;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private UserRepository uRep;
    @Autowired
    MessageRepository mRep;
    public static String adminId;

    @PostMapping("/api/v1/data/reset/msg")
    public void resetMessages(@RequestParam("password") String password) {
        if (!isAdmin(password)) {
            throw new UnauthorizedException();
        }
        mRep.deleteAll();
    }

    @PostMapping("/api/v1/data/reset/users")
    public void resetUsers(@RequestParam("password") String password) {
        if (!isAdmin(password)) {
            throw new UnauthorizedException();
        }
        uRep.deleteAll();
    }

    @PostMapping("/api/v1/data/users")
    public Iterable<User> getUsers(@RequestParam("password") String password) {
        if (!isAdmin(password)) {
            throw new UnauthorizedException();
        }
        return uRep.findAll();
    }

    @PostMapping("/api/v1/data/users/count")
    public Long getUserCount(@RequestParam("password") String password) {
        if (!isAdmin(password)) {
            throw new UnauthorizedException();
        }
        return uRep.count();
    }

    @PostMapping("/api/v1/game/start")
    public void startGame(@RequestParam("password") String password) {
        if (!isAdmin(password)) {
            throw new UnauthorizedException();
        }

        if (uRep.count() % 2 != 0) {
            throw new IllegalGameStateException();
        }

        List<User> users = new ArrayList<User>();
        uRep.findAllReady().forEach(users::add);
        Collections.shuffle(users);

        for (int i = 0; i < users.size(); i += 2) {
            User firstUser = users.get(i);
            User secondUser = users.get(i + 1);
            firstUser.setAsking();
            firstUser.setPartner(secondUser.getId());
            secondUser.setPartner(firstUser.getId());
        }

        GameController.isGameOn = true;
    }

    // @PostMapping("/api/v1/game/ready")
    // public 

    private Boolean isAdmin(String password) {
        return password.equals(adminId);
    }
}