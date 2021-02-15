package com.patrick.Runners.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

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

  }
