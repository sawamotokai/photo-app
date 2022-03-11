package com.photoapp.users.users.service;

import java.util.UUID;

import com.photoapp.users.users.data.UserEntity;
import com.photoapp.users.users.data.UserRepository;
import com.photoapp.users.users.shared.UserDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  UserRepository repo;

  @Autowired
  public UserServiceImpl(UserRepository repo) {
    this.repo = repo;
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    userDto.setUserId(UUID.randomUUID().toString());
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

    userEntity.setEncryptedPassword("test");
    repo.save(userEntity);
    return userDto;
  }
}
