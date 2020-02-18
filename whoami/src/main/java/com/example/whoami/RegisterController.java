package com.example.whoami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  @RequestMapping("/api/v1/user/register")
  @ResponseBody
  public String registerUser(@RequestParam("userName") String userName, UserRepository uRep) {
    User ret = this.uRep.findByUserName(userName);
    if (ret != null) {
      throw new UserNameTakenException();
    }

    User myUser = new User(userName, userName.equals(WhoamiBackend.adminUser));
    this.uRep.save(myUser);

    if (userName.equals(WhoamiBackend.adminUser))
      AdminController.adminId = myUser.getId();

    log.info(myUser.toString());
    return myUser.getId();
  }
}
