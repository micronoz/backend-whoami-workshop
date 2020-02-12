package com.example.whoami;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {

  @GetMapping("/hello-world")
  @ResponseBody
  public String sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
    return "Hello World!";
  }
}