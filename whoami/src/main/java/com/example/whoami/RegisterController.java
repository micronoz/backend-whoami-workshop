package com.example.whoami;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class RegisterController {
  private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
  private UserRepository uRep;

  public RegisterController(UserRepository uRep) {
    this.uRep = uRep;
  }

  @GetMapping("/hello-world")
  @ResponseBody
  public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
    return "Hello World!";
  }

  @RequestMapping("/api/v1/user/register")
  @ResponseBody
  public Long registerUser(@RequestParam("userName") String userName, UserRepository uRep) {
    List<User> ret = this.uRep.findByUserName(userName);
    if (ret.size() != 0) {
      throw new UserNameTakenException();
    }
    User myUser = new User(userName);
    this.uRep.save(myUser);
    log.info(String.format("Registered user: %s, with id: %d", userName, myUser.getId()));
    return myUser.getId();
  }
}
