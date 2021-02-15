package com.patrick.Runners.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnerRepository;
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

  @GetMapping("/register")
  public String showAddRunnerForm(Model model) {
    Runner runner = new Runner();

    model.addAttribute("runner",runner);
    System.out.println("redirecting to addRunner form");
    return "addRunner";

  }

  @PostMapping("/addRunner")
  public String submitAddRunnerForm(@ModelAttribute("runner") Runner newRunner){
    //System.out.println(newRunner.getFirstName() + " " + newRunner.getUsername()); // this getUsername returns null
    Runner runner = new Runner(newRunner.getFirstName(), newRunner.getLastName(), newRunner.getInstagramHandle()); // a runner object was not default created, I had to call the Runner consturctor myself
    saveRunner(runner);
    return "addRunnerSuccess";
  }

  public void saveRunner(Runner runner){
    RunnersDaoService.saveRunner(runner); // did not need to Autowire/inject RunnersDaoService here
  }




}
