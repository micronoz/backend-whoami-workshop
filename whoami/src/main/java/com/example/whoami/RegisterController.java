package com.example.whoami;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
  private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

  @Autowired
  private UserRepository uRep;

  @GetMapping("/hello-world")
  @ResponseBody
  public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
    return "Hello World!";
  }

  @PostMapping("/api/v1/user/register")
  @ResponseBody
  public String registerUser(@RequestBody IncomingUserName userName) {
    User ret = this.uRep.findByUserName(userName.userName);
    if (ret != null) {
      throw new UserNameTakenException();
    }
    User myUser = new User(userName.userName);
    this.uRep.save(myUser);

    log.info(myUser.toString());
    return myUser.getId();
  }
}
