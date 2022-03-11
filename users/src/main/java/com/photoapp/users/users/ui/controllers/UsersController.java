package com.photoapp.users.users.ui.controllers;

import javax.validation.Valid;

import com.photoapp.users.users.service.UserService;
import com.photoapp.users.users.shared.UserDto;
import com.photoapp.users.users.ui.model.CreateUserReqModel;
import com.photoapp.users.users.ui.model.CreateUserResModel;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  UserService service;

  @GetMapping("/status/check")
  public String status() {
    return "Running on port " + env.getProperty("local.server.port");
  }

  @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<CreateUserResModel> createUser(@Valid @RequestBody CreateUserReqModel userDetails) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserDto userDto = modelMapper.map(userDetails, UserDto.class);
    UserDto createdUser = service.createUser(userDto);
    CreateUserResModel body = modelMapper.map(createdUser, CreateUserResModel.class);
    return ResponseEntity.status(HttpStatus.CREATED).body(body);
  }

}
