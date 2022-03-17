package com.photoapp.users.users.service;

import com.photoapp.users.users.shared.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  UserDto createUser(UserDto userDto);
}
