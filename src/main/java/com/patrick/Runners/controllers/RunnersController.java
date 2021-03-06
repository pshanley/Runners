package com.patrick.Runners.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

import com.patrick.Runners.instagram.GetInstagramDetails;
import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDaoService;

@Controller
public class RunnersController {

  @RequestMapping("/")
  public String listRunner(Model model) {

    RunnersDaoService business = new RunnersDaoService();
    List<Runner> runnersList = business.getRunnersList();

    model.addAttribute("runners", runnersList);

    return "runners";
  }

  @GetMapping("/addRunnerForm")
  //@PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public String showAddRunnerForm(Model model) {
    Runner runner = new Runner();
    model.addAttribute("runner",runner);
    System.out.println("redirecting to addRunner form");
    return "addRunner";

  }

  @PostMapping("/addRunner")
  //@PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public String submitAddRunnerForm(@ModelAttribute("runner") Runner runner){
    runner.setUsername(runner.getFirstName().toUpperCase() + "_" + runner.getLastName().toUpperCase());
    GetInstagramDetails.getInstagramDetails(runner);
    saveRunner(runner);
    System.out.println(runner.getInstagramHandle() + "has this many followers: " + runner.getFollowersCount());
    return "addRunnerSuccess";
  }

  public void saveRunner(Runner runner){
    RunnersDaoService.saveRunner(runner); // did not need to Autowire/inject RunnersDaoService here
  }

}
