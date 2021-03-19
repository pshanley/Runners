package com.patrick.Runners.runner.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.patrick.Runners.RunnersApplication;
import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnerRepository;
import com.patrick.Runners.runner.RunnersDaoService;
import com.patrick.Runners.teams.Team;
import com.patrick.Runners.teams.TeamDaoService;
import com.patrick.Runners.teams.TeamRepository;

public class RunnersTest {


  @Test
  public void getSingleRunner(){
    String username = "MO_FARAH";

    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnerRepository runnerRepository = configurableApplicationContext.getBean(RunnerRepository.class);

    Runner runner = runnerRepository.findByUsername(username);
    //List<Runner> runnersList = runnerRepository.findAll();

    System.out.println(runner.getInstagramHandle());
  }

  @Test
  public void getAllRunnersNotOnTeam(){
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnersDaoService runnersDaoService = configurableApplicationContext.getBean(RunnersDaoService.class);
    TeamDaoService teamDaoService = configurableApplicationContext.getBean(TeamDaoService.class);
    Team team = teamDaoService.getSingleTeam("Nike Oregon Project");

    List<Runner> runnerListNotOnNop = runnersDaoService.getAllRunnersNotOnTeam(team);
    for(Runner r: runnerListNotOnNop){
      System.out.println(r.getUsername());
    }

  }
}
