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
  List<Runner> removedRunners = new ArrayList<>();

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
    teamDaoService.saveTeam(team);
  }

  @PostMapping("/teams/saveTeamChanges")
  public ModelAndView saveTeamChanges(){

    teamDaoService.saveTeam(localTeam); // saving the team updates the runners that were added
    for(Runner r: removedRunners){
      RunnersDaoService.saveRunner(r); // but...saving isn't updating the runners that were removed, have to do it myself
                                      // Not sure if I configured the One to Many relationship wrong or that's just how it is
    }

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("/teams/team"); // this refers to the .jsp
    modelAndView.addObject("team",localTeam);
    removedRunners.clear();

    return modelAndView;  // the view is trying to find "/teams/styles.css" and not "/styles.css" like when I pass the view above/
                          // It's retrieving the css from static/teams/styles.css
  }



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

    Runner runnerToReAdd = null;
    Runner runner = runnersDaoService.getSingleRunner(runnerName);
    localTeam.addAthletes(runner);
    runner.setTeam(localTeam);


    // this is kind of sloppy. The Runner obj I added to the removedRunners list is not the same as the object
    // I pull from the database in this method. Instead I compare by username and if there is a match I assign
    // a new object
    for(Runner r: removedRunners){
      if(r.getUsername().equals(runnerName)) {
        runnerToReAdd = r;
      }
    }
    removedRunners.remove(runnerToReAdd); // runnerToReAdd references(?) this same object in the list

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

    for(Runner r : localTeam.getAthletes()){ // not sure if there is a more efficient way to do this, but other functions might iterate through a list behind the scenes so it might not even matter
      if(r.getUsername().equals(runnerName)){
         runner = r;
        runner.setTeam(null);
        removedRunners.add(runner);
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

