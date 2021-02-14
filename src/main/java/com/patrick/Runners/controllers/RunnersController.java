package com.patrick.Runners.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDao;

@RestController
public class RunnersController {
  @RequestMapping("/list_runners")
  public String listRunner(Model model) {

    RunnersDao business = new RunnersDao();
    List<Runner> runnersList = business.getRunnersList();

    model.addAttribute("runners", runnersList);

    return "runners";
  }

}
