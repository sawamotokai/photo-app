package com.photoapp.users.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoapp.users.users.service.UserService;
import com.photoapp.users.users.shared.UserDto;
import com.photoapp.users.users.ui.model.LoginReqModel;

import org.springframework.security.core.userdetails.User;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private UserService userService;
  private Environment env;

  public AuthenticationFilter(UserService userService, Environment env, AuthenticationManager authenticationManager) {
    this.env = env;
    this.userService = userService;
    super.setAuthenticationManager(authenticationManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      LoginReqModel creds = new ObjectMapper().readValue(request.getInputStream(), LoginReqModel.class);
      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    String username = ((User) authResult.getPrincipal()).getUsername();
    UserDto userDto = userService.getUserDetailsByEmail(username);
    // String token = Jwts.builder().setSubject(userDto.getUserId())
    // .setExpiration(new Date(System.currentTimeMillis() +
    // Long.parseLong(env.getProperty("token.expiration_time"))))
    // .signWith(SignatureAlgorithm.HS512,
    // env.getProperty("token.secret")).compact();
    String token = JWT.create()
        .withSubject(userDto.getUserId())
        .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
        .withIssuedAt(new Date())
        .withIssuer("PhotoApp")
        .sign(Algorithm.HMAC256(env.getProperty("token.secret")));
    response.addHeader("token", token);
    response.addHeader("userId", userDto.getUserId());
  }
}
