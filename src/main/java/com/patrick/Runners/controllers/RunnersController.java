package com.patrick.Runners.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import com.patrick.Runners.instagram.GetInstagramDetails;
import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDaoService;

@Controller
public class RunnersController {

  RunnersDaoService runnersDaoService = new RunnersDaoService();

  @RequestMapping("/")
  public String listRunner(Model model) {


    List<Runner> runnersList = runnersDaoService.getRunnersList();
    System.out.println(runnersList);
    runnersList.sort(Comparator.comparing(Runner::getFollowersCount).reversed());
    System.out.println("Type of List Object: " + runnersList.getClass());
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
  public ModelAndView submitAddRunnerForm(@ModelAttribute("runner") Runner runner) throws IOException, InterruptedException {
    ModelAndView modelAndView = new ModelAndView();

    if (runner.getFirstName().equals("") && runner.getLastName().equals("")){
      modelAndView.setViewName("addRunner");
      modelAndView.addObject("error","First Name and Last Name are required");
      return modelAndView;

    } else if(runner.getFirstName().equals("")){
      modelAndView.setViewName("addRunner");
      modelAndView.addObject("error","First Name is required");
      return modelAndView;

    } else if(runner.getLastName().equals("")){
      modelAndView.setViewName("addRunner");
      modelAndView.addObject("error","Last Name is required");
      return modelAndView;

    }
    runner.setUsername(runner.getFirstName().toUpperCase() + "_" + runner.getLastName().toUpperCase());

    Runner runnerDb = runnersDaoService.getSingleRunner(runner.getUsername());
    if (runnerDb != null){
      modelAndView.setViewName("addRunner");
      modelAndView.addObject("error","Runner already exists");
      return modelAndView;
    }

    if (runner.getInstagramHandle().equals("")){
      System.out.println("NOT FETCHING INSTAGRAM");
    } else {
      Character c1 = runner.getInstagramHandle().charAt(0);
      if(c1.equals('@')){ // remove "@" from instagram handle if a user adds it
        runner.setInstagramHandle(runner.getInstagramHandle().substring(1));
      }
      Runner runnerInstagramCheck = runnersDaoService.getRunnerByInstagramHandle(runner.getInstagramHandle());
      if (runnerInstagramCheck != null){
        modelAndView.setViewName("addRunner");
        modelAndView.addObject("error","Runner with that instagram handle already exists");
        return modelAndView;
      }
      boolean instagramRequestSuccess = GetInstagramDetails.getInstagramDetails(runner);
      if(!instagramRequestSuccess){
        modelAndView.addObject("error","unable to connect to this runners's instagram");
      }
    }


    saveRunner(runner);
    modelAndView.setViewName("addRunnerSuccess");
    return modelAndView;
  }

  public void saveRunner(Runner runner){
    RunnersDaoService.saveRunner(runner); // did not need to Autowire/inject RunnersDaoService here
  }

  @RequestMapping(value = "/runners", method = RequestMethod.GET)
  public ModelAndView getRunner(@RequestParam String username){
    System.out.println(username);
    Runner runner = runnersDaoService.getSingleRunner(username);
    System.out.println(runner.getInstagramHandle());
    ModelAndView modelAndView = new ModelAndView("runner");
    modelAndView.addObject("runner",runner);

    return modelAndView;
  }





}
