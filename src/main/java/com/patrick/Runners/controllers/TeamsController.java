package com.patrick.Runners.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDaoService;
import com.patrick.Runners.teams.Team;
import com.patrick.Runners.teams.TeamDaoService;

@Controller
public class TeamsController {

  TeamDaoService teamDaoService = new TeamDaoService();
  RunnersDaoService runnersDaoService = new RunnersDaoService();

  @RequestMapping("/teams")
  public ModelAndView listTeams(@RequestParam(required = false) String teamName) {
    ModelAndView modelAndView = new ModelAndView();
    if (teamName != null){
     modelAndView.setViewName("teams/team");
      Team team = teamDaoService.getSingleTeam(teamName);
      System.out.println(team.getTeamName());
      System.out.println(team.getAthletes());
      modelAndView.addObject("team",team);
    }else {
      modelAndView.setViewName("teams/teams");
      List<Team> teamList = teamDaoService.getAllTeams();
      modelAndView.addObject("teams",teamList);
    }

    return modelAndView;
  }

  @GetMapping("/addTeamForm")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public String showAddTeamForm(Model model) {
    Team team = new Team();
    model.addAttribute("team",team);
    System.out.println("redirecting to addTeam form");
    return "teams/addTeam";

  }

  @PostMapping("/addTeam")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public String submitAddTeamForm(@ModelAttribute("team") Team team)
      throws IOException, InterruptedException {
    saveTeam(team);
    System.out.println(team.getTeamName());
    return "teams/addTeamSuccess";
  }


  private void saveTeam(Team team) {
    TeamDaoService.saveTeam(team);
  }

  @PostMapping("/teams/addRunner")
  public  String addRunnerToTeam(@RequestParam(name="teamName") String teamName, @RequestParam(name="runner") String runnerName){
    Team team = teamDaoService.getSingleTeam(teamName);
    System.out.println(team.getTeamName());

    Runner runner = runnersDaoService.getSingleRunner(runnerName);
    System.out.println(runner.getFirstName());

    return runner.getUsername();


  }

}

