package com.patrick.Runners.controllers;

import java.io.IOException;
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

import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDaoService;
import com.patrick.Runners.teams.Team;
import com.patrick.Runners.teams.TeamDaoService;

@Controller
public class TeamsController {

  @RequestMapping("/teams")
  public String listTeams(Model model) {

    TeamDaoService business = new TeamDaoService();
    List<Team> teamList = business.getAllTeams();
    model.addAttribute("teams", teamList);

    return "teams";
  }

  @GetMapping("/addTeamForm")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public String showAddTeamForm(Model model) {
    Team team = new Team();
    model.addAttribute("team",team);
    System.out.println("redirecting to addTeam form");
    return "addTeam";

  }

  @PostMapping("/addTeam")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public String submitAddTeamForm(@ModelAttribute("team") Team team)
      throws IOException, InterruptedException {
    saveTeam(team);
    System.out.println(team.getTeamName());
    return "addTeamSuccess";
  }

  private void saveTeam(Team team) {
    TeamDaoService.saveTeam(team);
  }



}
