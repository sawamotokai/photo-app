package com.photoapp.users.users.ui.controllers;

import javax.validation.Valid;

import com.photoapp.users.users.ui.model.CreateUserReqModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  @Autowired
  private Environment env;

  @GetMapping("/status/check")
  public String status() {
    return "Running on port " + env.getProperty("local.server.port");
  }

  @PostMapping
  public String createUser(@Valid @RequestBody CreateUserReqModel userDetails) {
    return "User Created";
  }

}
