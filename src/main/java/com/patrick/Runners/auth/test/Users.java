package com.patrick.Runners.auth.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.patrick.Runners.RunnersApplication;
import com.patrick.Runners.auth.AuthDaoService;
import com.patrick.Runners.auth.User;
import com.patrick.Runners.runner.RunnersDaoService;

public class Users {



  @Test
  public void getUsers(){
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    AuthDaoService authDaoService = configurableApplicationContext.getBean(AuthDaoService.class);
    List<User> users = authDaoService.getApplicationUsers();
    System.out.println("Found the following users: ");
    for(User user : users){
      System.out.println(user.getUsername());
    }
  }

}
