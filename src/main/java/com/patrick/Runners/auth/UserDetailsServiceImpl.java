package com.patrick.Runners.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {// This runsloadUserByUsername is the only method in UserDetailsService interface
    User user = userRepository.findUserByUsername(username); // Runs RDBMS query

    if (user == null) {
      System.out.println("can not find user");
      throw new UsernameNotFoundException("Could not find user");
    }

    return new MyUserDetails(user);

  }
}
