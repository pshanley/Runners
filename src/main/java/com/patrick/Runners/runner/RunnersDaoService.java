package com.patrick.Runners.runner;

import java.util.ArrayList;
import java.util.List;
import com.patrick.Runners.teams.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Service // this creates a Singleton
public class RunnersDaoService {

  private ApplicationContext context;
  @Autowired
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



  public static List<Runner> getRunnersList() {
    List<Runner> listRunners = repo.findAll();
    return listRunners;
  }

  public Runner getSingleRunner(String username){
    Runner runner = repo.findByUsername(username);
    return runner;
  }

  public Runner getRunnerByInstagramHandle(String instagramHandle){
    Runner runner = repo.findByInstagramHandle(instagramHandle);
    return runner;
  }


  public static List<Runner> getAllRunnersNotOnTeam(Team team) {
    List<Runner> runnersList = getRunnersList();
    List<Runner> runnersToRemove = new ArrayList<>(); // can't change the size of a List during for a loop
    for (Runner r : runnersList) {
      if (r.getTeam() != null ) {  // getting a NPE below if a runner wasnt on a team, don't use .equals()?
        if (r.getTeam().getTeamName().equals(team.getTeamName())) {// The Team object that I pass in is NOT the same as the Team object that the athletes reference, but both Teams have the same teamName
          runnersToRemove.add(r);
        }
      }
    }
    for(Runner r : runnersToRemove){
      runnersList.remove(r);
    }

    return runnersList;
  }

  public static List<Runner> getAllRunnersNotOnLocalTeam(Team team) {
    List<Runner> runnersList = getRunnersList();
    List<Runner> runnersToRemove = new ArrayList<>(); // can't change the size of a List during for a loop
    List<Runner> localRunnersList = team.getAthletes();
    List<String> localRunnersUsernames = new ArrayList<>();

    for(Runner r: localRunnersList){
      localRunnersUsernames.add(r.getUsername());
    }

    for (Runner r: runnersList){
      if(localRunnersUsernames.contains(r.getUsername())){
        runnersToRemove.add(r);
      }
    }

    for (Runner r: runnersToRemove){
      runnersList.remove(r);
    }

    return runnersList;

  }
}
