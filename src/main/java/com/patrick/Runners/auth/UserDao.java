package com.patrick.Runners.auth;

import java.util.Optional;

public interface UserDao {
  public Optional<User> selectApplicationUserByUsername(String username);

}
