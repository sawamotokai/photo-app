package com.photoapp.users.users.service;

import java.util.ArrayList;
import java.util.UUID;

import com.photoapp.users.users.data.UserEntity;
import com.photoapp.users.users.data.UserRepository;
import com.photoapp.users.users.shared.UserDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  UserRepository repo;
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository repo, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.repo = repo;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    userDto.setUserId(UUID.randomUUID().toString());
    userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

    repo.save(userEntity);

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = repo.findByEmail(username);
    if (user == null)
      throw new UsernameNotFoundException(username);
    return new User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
  }

  @Override
  public UserDto getUserDetailsByEmail(String email) {
    UserEntity user = repo.findByEmail(email);
    if (user == null)
      throw new UsernameNotFoundException(email);
    return new ModelMapper().map(user, UserDto.class);
  }
}
