package com.patrick.Runners.teams;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnerRepository;

@Service
public class TeamDaoService {

  private ApplicationContext context;
  @Autowired
  public static TeamRepository repo;
  public static RunnerRepository runnerRepo;

  @Autowired
  public TeamDaoService(ApplicationContext context) {
    this.context = context;
    this.repo = context.getBean(TeamRepository.class);
  }

  public TeamDaoService(){}

  public void saveTeam(Team team){
    repo.saveAndFlush(team);

    System.out.println("saved the following Team to RDBMS: " + team.getTeamName());
  }

  public List<Team> getAllTeams() {
    List<Team> listTeams = repo.findAll();
    return listTeams;
  }

  public void deleteTeam(Team team){
    repo.delete(team);
  }

  public Team getSingleTeam(String teamName){
    Team team = repo.findByTeamName(teamName);
    return team;
  }

  public  void addRunnerToTeam(Team team, Runner runner){
    List<Runner> runnersOnTeam = team.getAthletes();
    runnersOnTeam.add(runner);
    team.setAthletes(runnersOnTeam);
    runner.setTeam(team);
    saveTeam(team);
  }

  public Team removeRunnerFromTeam(Team team, Runner runner){

    List<Runner> runnersList = team.getAthletes();
    runnersList.remove(runner); // this is deleting the object, I don't want that
    team.setAthletes(runnersList);

    // saving the team isn't removing the Team ID from the runner... so I have to set it myself
    // I might only have to do the following but I want to update the team in memory
    runner.setTeam(null);

    for (Runner r: team.getAthletes()){
      System.out.println("STILL ON THE TEAM DAO: " + r.getUsername());
    }

    return team;
    //RunnersDaoService.saveRunner(runner);

  }
}
