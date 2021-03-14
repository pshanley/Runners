package com.patrick.Runners.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import com.patrick.Runners.instagram.GetInstagramDetails;
import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDaoService;

@Controller
public class RunnersController {

  RunnersDaoService business = new RunnersDaoService();

  @RequestMapping("/")
  public String listRunner(Model model) {


    List<Runner> runnersList = business.getRunnersList();
    runnersList.sort(Comparator.comparing(Runner::getFollowersCount).reversed());
    model.addAttribute("runners", runnersList);

    return "runners";
  }

  @GetMapping("/addRunnerForm")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public String showAddRunnerForm(Model model) {
    Runner runner = new Runner();
    model.addAttribute("runner",runner);
    System.out.println("redirecting to addRunner form");
    return "addRunner";

  }

  @PostMapping("/addRunner")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public String submitAddRunnerForm(@ModelAttribute("runner") Runner runner)
      throws IOException, InterruptedException {
    runner.setUsername(runner.getFirstName().toUpperCase() + "_" + runner.getLastName().toUpperCase());
    GetInstagramDetails.getInstagramDetails(runner);
    saveRunner(runner);
    System.out.println(runner.getInstagramHandle() + "has this many followers: " + runner.getFollowersCount());
    return "addRunnerSuccess";
  }

  public void saveRunner(Runner runner){
    RunnersDaoService.saveRunner(runner); // did not need to Autowire/inject RunnersDaoService here
  }

  @RequestMapping(value = "/runners", method = RequestMethod.GET)
  public ModelAndView getRunner(@RequestParam String username){
    System.out.println(username);
    Runner runner = business.getSingleRunner(username);
    System.out.println(runner.getInstagramHandle());
    ModelAndView modelAndView = new ModelAndView("runner");
    modelAndView.addObject("runner",runner);

    return modelAndView;
  }





}
