package com.example.whoami;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

  @GetMapping("/hello-world")
  @ResponseBody
  public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
    return "Hello World!";
  }

  @PostMapping("/api/v1/{username}")
  public String registerUser(@PathVariable String username) {
    return "";
  }

}
