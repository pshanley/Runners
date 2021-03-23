package com.patrick.Runners.controllers;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping(
    method={RequestMethod.POST,RequestMethod.GET}
)
public class TeamsController {

  TeamDaoService teamDaoService = new TeamDaoService();
  RunnersDaoService runnersDaoService = new RunnersDaoService();
  public Team localTeam;

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
  public ModelAndView addRunnerToTeam(@RequestParam(name="teamName") String teamName, @RequestParam(name="runner") String runnerName){
    System.out.println("Adding Runner to Team");
    Team team = teamDaoService.getSingleTeam(teamName);
    System.out.println(team.getTeamName());

    Runner runner = runnersDaoService.getSingleRunner(runnerName);
    System.out.println(runner.getFirstName());
    teamDaoService.addRunnerToTeam(team,runner);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("teams/addRunnerToTeam");
    modelAndView.addObject("team",team);

    List<Runner> athletesNotOnTeam = RunnersDaoService.getAllRunnersNotOnTeam(team);
    modelAndView.addObject("athletesNotOnTeam", athletesNotOnTeam);

    return modelAndView;  // the view is trying to find "/teams/styles.css" and not "/styles.css" like when I pass the view above/
                          // It's retrieving the css from static/teams/styles.css
  }

  /*@GetMapping("/addRunnerToTeamForm")
  public ModelAndView showAddRunnerToTeamForm(@RequestParam(name="teamName") String teamName) {
    System.out.println("redirecting to addTeam form for Team: " + teamName);
    Team team = teamDaoService.getSingleTeam(teamName);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("teams/addRunnerToTeam");
    modelAndView.addObject("team",team);

    List<Runner> athletesNotOnTeam = RunnersDaoService.getAllRunnersNotOnTeam(team);
    modelAndView.addObject("athletesNotOnTeam", athletesNotOnTeam);

    // need to create a model and add the team to that
    return modelAndView;
  }*/


  // ****************************************************************************** //

  @GetMapping("/showEditTeamForm")
  public ModelAndView showEditTeamForm(@RequestParam(name="teamName") String teamName) {
    localTeam = teamDaoService.getSingleTeam(teamName);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("teams/editTeamForm");
    modelAndView.addObject("team",localTeam);

    List<Runner> athletesNotOnTeam = RunnersDaoService.getAllRunnersNotOnTeam(localTeam);
    modelAndView.addObject("athletesNotOnTeam", athletesNotOnTeam);

    // need to create a model and add the team to that
    return modelAndView;
  }

  @PostMapping("/teams/addRunnerLocal")
  public ModelAndView addRunnerToLocalTeam(@RequestParam(name="teamName") String team, @RequestParam(name="runner") String runnerName){
    // This method will be called when someone adds a runner to the team on a form, before submitting
    // Stores a local team object that is not persisted to the database
    // need to pass in the runners already on the team, and not repeatedly get that from RDBMS (or maybe not, this could just have the change before submitting)

    // This is fetching the team from RDBMS each time and overriding the previous local edition
    //Team team = teamDaoService.getSingleTeam(teamName);


    Runner runner = runnersDaoService.getSingleRunner(runnerName);
    localTeam.addAthletes(runner);
    runner.setTeam(localTeam);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("teams/editTeamForm");
    modelAndView.addObject("team",localTeam);

    List<Runner> athletesNotOnTeam = RunnersDaoService.getAllRunnersNotOnLocalTeam(localTeam);
    modelAndView.addObject("athletesNotOnTeam", athletesNotOnTeam);

    return modelAndView;  // the view is trying to find "/teams/styles.css" and not "/styles.css" like when I pass the view above/
    // It's retrieving the css from static/teams/styles.css
  }

  @PostMapping("/teams/removeRunnerLocal")
  public ModelAndView removeRunnerFromTeamLocal(@RequestParam(name="teamName") String teamName, @RequestParam(name="runner") String runnerName){
    Runner runner = null;
    List<Runner> localTeamAthletes = localTeam.getAthletes();

    for(Runner r : localTeam.getAthletes()){
      if(r.getUsername().equals(runnerName)){
         runner = r;
        runner.setTeam(null);
      }
    }

    localTeamAthletes.remove(runner);
    localTeam.setAthletes(localTeamAthletes);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("teams/editTeamForm");
    modelAndView.addObject("team",localTeam);

    List<Runner> athletesNotOnTeam = RunnersDaoService.getAllRunnersNotOnLocalTeam(localTeam);
    modelAndView.addObject("athletesNotOnTeam", athletesNotOnTeam);

    return modelAndView;  // the view is trying to find "/teams/styles.css" and not "/styles.css" like when I pass the view above/
    // It's retrieving the css from static/teams/styles.css
  }





}

