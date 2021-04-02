package com.patrick.Runners.auth;

import javax.validation.constraints.NotNull;

public class UserDTO {

  @NotNull
  private String username;

  @NotNull
  private String password;

  @NotNull
  private String matchingPassword;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMatchingPassword() {
    return matchingPassword;
  }

  public void setMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }
}
