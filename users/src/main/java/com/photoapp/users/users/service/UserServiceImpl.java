package com.photoapp.users.users.service;

import java.util.UUID;

import com.photoapp.users.users.shared.UserDto;

public class UserServiceImpl implements UserService {

  @Override
  public UserDto createUser(UserDto userDto) {
    userDto.setUserId(UUID.randomUUID().toString());
    return userDto;
  }
}
