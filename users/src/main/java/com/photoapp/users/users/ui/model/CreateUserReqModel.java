package com.photoapp.users.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserReqModel {

  @NotNull(message = "First name cannot be null")
  @Size(min = 2, message = "First name must be at least 2 characters long")
  public String firstName;

  @NotNull(message = "Last name cannot be null")
  @Size(min = 2, message = "Last name must be at least 2 characters long")
  public String lastName;

  @NotNull(message = "Email cannot be null")
  @Email
  public String email;

  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  public String password;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
