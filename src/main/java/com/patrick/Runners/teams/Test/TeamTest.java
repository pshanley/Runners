package com.patrick.Runners.teams.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;



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

//@DataJpaTest
public class TeamTest {



  @Test
  public void createTeam(){
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    TeamRepository teamRepository = configurableApplicationContext.getBean(TeamRepository.class);
    Team team = new Team("Bowerman Track Club");
    teamRepository.save(team);
  }

  @Test
  public void getSingleTeam(){
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    TeamRepository teamRepository = configurableApplicationContext.getBean(TeamRepository.class);

    Team team = teamRepository.findByTeamName("Nike Oregon Project");
    System.out.println(team.getTeamName());
    System.out.println(team.getAthletes());

    List<Runner> runners = team.getAthletes();
    for(Runner r : runners){
      System.out.println(r.getUsername());
    }
  }

  @Test
  public void removeRunnerFromTeam(){
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnersDaoService runnersDaoService = configurableApplicationContext.getBean(RunnersDaoService.class);
    TeamDaoService teamDaoService = configurableApplicationContext.getBean(TeamDaoService.class);

    Runner runner = new Runner("Remove","Me","instagramhandle");
    RunnersDaoService.saveRunner(runner);
    Team team = teamDaoService.getSingleTeam("Nike Oregon Project");


    teamDaoService.addRunnerToTeam(team,runner);



    teamDaoService.removeRunnerFromTeam(team,runner);


  }

  @Test
  public void addExistingRunnerToExistingTeam(){
    String teamName = "Nike Oregon Project";
    String runnerUsername = "MO_FARAH";

    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    TeamRepository teamRepository = configurableApplicationContext.getBean(TeamRepository.class);
    RunnerRepository runnerRepository = configurableApplicationContext.getBean(RunnerRepository.class);

    List<Team> teamList = teamRepository.findAll();
    List<Runner> runnersList = runnerRepository.findAll();


    HashMap<String, Team> teamMap = new HashMap<String, Team>();
    for(Team t: teamList){
      System.out.println(t.getTeamName());
      teamMap.put(t.getTeamName(),t);
    }

    HashMap<String, Runner> runnersMap = new HashMap<String, Runner>();
    for(Runner r: runnersList){
      runnersMap.put(r.getUsername(),r);
    }

    Team nop = teamMap.get(teamName);
    Runner mo = runnersMap.get(runnerUsername);

    // maybe can use the AddAthletes method rather than making a list here
    List<Runner> athletesOnTeam = new ArrayList<>();
    athletesOnTeam.add(mo);
    nop.setAthletes(athletesOnTeam);

    mo.setTeam(nop);
    teamRepository.saveAndFlush(nop);
    System.out.println(nop.getAthletes());

  }

  @Test
  public void createTeamWithNewAthletes(){
    SpringApplication.run(RunnersApplication.class);
    Team team = new Team("Nike Oregon Project");
    Runner galen = new Runner("Galen", "Rupp", "galenruppofficial", team);
    Runner patrick = new Runner("Patrick", "Hanley", "grc_running", team);
    List<Runner> athletesOnTeam = Arrays.asList(galen, patrick);
    team.setAthletes(athletesOnTeam);
    //TeamDaoService.saveTeam(team);
    // When Cascade=ALL This is able to create new runners and then create a new team, and store everything in the database
    // The runners have a column corresponding to the Team ID
  }


  @Test
  public void CreateTeamWithExistingAthletes(){

    // I'm having trouble injecting some dependencies w/out starting the whole app

    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    TeamRepository teamRepository = configurableApplicationContext.getBean(TeamRepository.class);
    RunnerRepository runnerRepository = configurableApplicationContext.getBean(RunnerRepository.class);
    RunnersDaoService runnersService = new RunnersDaoService();
    List<Runner> runnersList = runnersService.getAllRunners();

    HashMap<String, Runner> runnersMap = new HashMap<String, Runner>();
    for(Runner r: runnersList){
      runnersMap.put(r.getUsername(),r);
    }

    Team team = new Team("Nike Oregon Project");

    Runner galen = runnersMap.get("BEN_TRUE");
    Runner mo = runnersMap.get("MO_FARAH");

    mo.setTeam(team);
    galen.setTeam(team);
    List<Runner> athletesOnTeam = new ArrayList<>();
    athletesOnTeam.add(galen);
    athletesOnTeam.add(mo);
    team.setAthletes(athletesOnTeam);
    teamRepository.saveAndFlush(team);
    runnerRepository.save(galen); // When cascade=MERGE saving the team wasn't updating the runner. When cascadeType = All/Persist I was getting errors that I couldn't fix

  }


}
