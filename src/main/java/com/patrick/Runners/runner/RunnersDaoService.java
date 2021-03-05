package com.patrick.Runners.runner;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service // this creates a Singleton
public class RunnersDaoService {

  private ApplicationContext context;
  public static RunnerRepository repo;

  public RunnersDaoService() {
  }

  @Autowired // the "repo" in savedRunner was null without the Autowired
  public RunnersDaoService(ApplicationContext context) {
    this.context = context;
    this.repo = context.getBean(RunnerRepository.class);
  }



  public static void saveRunner(Runner runner){
    System.out.println("tryint to store a runner to RDBMS");

    repo.save(runner);
    System.out.println("saved the following Runner to RDBMS: " + runner.getUsername());
  }



  public List<Runner> getRunnersList() {
    List<Runner> listRunners = repo.findAll();
    return listRunners;
  }


}
