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

  @Autowired
  public TeamDaoService(ApplicationContext context) {
    this.context = context;
    this.repo = context.getBean(TeamRepository.class);
  }

  public TeamDaoService(){}

  public static void saveTeam(Team team){
    repo.save(team);
    System.out.println("saved the following Runner to RDBMS: " + team.getTeamName());
  }

  public List<Team> getAllTeams() {
    List<Team> listTeams = repo.findAll();
    return listTeams;
  }

  public Team getSingleTeam(String teamName){
    Team team = repo.findByTeamName(teamName);
    return team;
  }
}
