package com.example.whoami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private UserRepository uRep;
    @Autowired
    MessageRepository mRep;
    public static String adminId;

    @PostMapping("/api/v1/game/status")
    @ResponseBody
    public String gameStatus(@RequestParam("id") String id) {
        if (!isAdmin(id)) {
            throw new UnauthorizedException();
        }
        return GameController.isGameOn.toString();
    }

    @PostMapping("/api/v1/data/reset/msg")
    public void resetMessages(@RequestParam("id") String id) {
        if (!isAdmin(id)) {
            throw new UnauthorizedException();
        }
        mRep.deleteAll();
    }

    @PostMapping("/api/v1/data/reset/users")
    public void resetUsers(@RequestParam("id") String id) {
        if (!isAdmin(id)) {
            throw new UnauthorizedException();
        }
        uRep.deleteExcept(adminId);;
    }

    @PostMapping("/api/v1/data/users")
    public Iterable<User> getUsers(@RequestParam("id") String id) {
        if (!isAdmin(id)) {
            throw new UnauthorizedException();
        }
        return uRep.findAll();
    }

    private Boolean isAdmin(String id) {
        return id.equals(adminId);
    }
}