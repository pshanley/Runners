package com.patrick.Runners.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.patrick.Runners.instagram.GenerateCookies;
import com.patrick.Runners.instagram.GetInstagramDetails;
import com.patrick.Runners.runner.DailyFollowerCount;
import com.patrick.Runners.runner.DailyFollowerDaoService;
import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDaoService;
import com.patrick.Runners.teams.Team;
import com.patrick.Runners.teams.TeamDaoService;
import com.patrick.Runners.uploads.FileUploadController;
import com.patrick.Runners.uploads.FileUploadService;

@Controller
public class RunnersController {

  @Autowired
  FileUploadService fileUploadService;

  DailyFollowerDaoService dailyFollowerDaoService = new DailyFollowerDaoService();

  RunnersDaoService runnersDaoService = new RunnersDaoService();
  TeamDaoService teamDaoService = new TeamDaoService();

  @RequestMapping("/")
  public String listRunner(Model model, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "25") Integer pageSize, @RequestParam(defaultValue = "followersCount") String sortBy) {
    List<Runner> runnersList = runnersDaoService.getRunnersPaged(pageNo,pageSize,sortBy);
    System.out.println(runnersList);
    //runnersList.sort(Comparator.comparing(Runner::getFollowersCount).reversed());
    System.out.println("Type of List Object: " + runnersList.getClass());
    Long numberOfRunners = runnersDaoService.getNumberOfRunners();
    int numberOfPages = runnersDaoService.getNumberOfPages(numberOfRunners,pageSize);
    String currentViewedRunnersString = runnersDaoService.getCurrentRunners(numberOfRunners, pageNo, pageSize);
    model.addAttribute("numberOfRunners", numberOfRunners);
    model.addAttribute("currentViewedRunners", currentViewedRunnersString);
    model.addAttribute("currentPage", pageNo);
    model.addAttribute("runners", runnersList);
    model.addAttribute("numberOfPages", numberOfPages);

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
  public ModelAndView submitAddRunnerForm(Principal user, @ModelAttribute("runner") Runner runner,@RequestParam(name="file") MultipartFile file) throws IOException, InterruptedException {
    ModelAndView modelAndView = new ModelAndView();

    runner.setUsername(runner.getFirstName().toUpperCase() + "_" + runner.getLastName().toUpperCase());
    runner.setUserWhoAdded(user.getName());

    String runnerAdditionError = addRunnerValidations(runner);
    if(!runnerAdditionError.equals("")){
      modelAndView.addObject("error",runnerAdditionError);
      modelAndView.setViewName("addRunner");
      return modelAndView;
    }

    String fileUploadValidation = FileUploadController.addFileUploadValidations(file);
    if (!fileUploadValidation.equals("")) {
      modelAndView.addObject("error",fileUploadValidation);
      modelAndView.setViewName("addRunner");
      return modelAndView;
    }


    if (runner.getInstagramHandle().equals("")){
      System.out.println("NOT FETCHING INSTAGRAM");
    } else {
      Character c1 = runner.getInstagramHandle().charAt(0);
      if(c1.equals('@')){ // remove "@" from instagram handle if a user adds it
        runner.setInstagramHandle(runner.getInstagramHandle().substring(1));
      }
      int instagramRequestSuccess = GetInstagramDetails.getInstagramDetails(runner);
      if(instagramRequestSuccess == -1){
        modelAndView.addObject("error","unable to connect to this runners's instagram");
      }
    }

    fileUploadService.FileUpload(file, runner.getUsername());
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

    List<Map<LocalDate,Integer>> listOfFollowerCounts = getMapOfDailyFollowers(runner);

    modelAndView.addObject("dataPoints", listOfFollowerCounts);




    return modelAndView;
  }

  @GetMapping("/editRunnerForm")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public ModelAndView showEditRunnerForm(String runnerName) {
    ModelAndView modelAndView = new ModelAndView();
    System.out.println(runnerName);
    Runner runner = runnersDaoService.getSingleRunner(runnerName);
    modelAndView.setViewName("runners/editRunner");
    modelAndView.addObject("runner",runner);

    List<Team> teamList = teamDaoService.getAllTeams();
    modelAndView.addObject("teams",teamList);

    System.out.println("redirecting to editRunner form");
    return modelAndView;
  }

  @PostMapping("/runners/addToTeam")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public ModelAndView addToTeam(String runnerName, String teamName) {
    ModelAndView modelAndView = new ModelAndView();
    System.out.println(runnerName);
    Runner runner = runnersDaoService.getSingleRunner(runnerName);
    Team team = teamDaoService.getSingleTeam(teamName);
    runner.setTeam(team);
    if(team != null) {
      team.addAthletes(runner);
    }else{
      runner.setTeam(null);
    }

    RunnersDaoService.saveRunner(runner);

    modelAndView.setViewName("runner");
    modelAndView.addObject("runner",runner);

    return modelAndView;

  }

  @PostMapping("/runners/updateRunnerPicture")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public ModelAndView setNewImageForRunner(String newImageUrl, String runnerName) {
    ModelAndView modelAndView = new ModelAndView();
    System.out.println(newImageUrl);
    Runner runner = runnersDaoService.getSingleRunner(runnerName);
    runner.setImageURL(newImageUrl);

    RunnersDaoService.saveRunner(runner);

    modelAndView.setViewName("runner");
    modelAndView.addObject("runner",runner);

    return modelAndView;

  }

  @PostMapping("/runners/delete")
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public ModelAndView deleteRunner(String runnerName){
    Runner runner = runnersDaoService.getSingleRunner(runnerName);
    runnersDaoService.deleteRunner(runner);
    return new ModelAndView("redirect:/");
  }

  public String addRunnerValidations(Runner runner){
    Runner runnerInstagramCheck;
    Runner runnerDb = runnersDaoService.getSingleRunner(runner.getUsername());
    if(!runner.getInstagramHandle().equals("")) {
       runnerInstagramCheck = runnersDaoService.getRunnerByInstagramHandle(runner.getInstagramHandle());
    }else{
       runnerInstagramCheck = null;
    }

    if (runner.getFirstName().equals("") && runner.getLastName().equals("")){
      return "First Name and Last Name are required";
    } else if(runner.getFirstName().equals("")){
      return "First Name is required";
    } else if(runner.getLastName().equals("")){
      return "Last Name is required";
    } else if(runner.getInstagramHandle().contains("/")){
        return "Please insert just the instagram handle, not the full URL";
    } else if (runnerDb != null) {
        return "Runner with this name already exists";
    } else if (runnerInstagramCheck !=null){
      return "Runner with this Instagram Handle already exists";
    } else {
      return "";
    }

  }


  public List<Map<LocalDate,Integer>> getMapOfDailyFollowers(Runner runner) {
    List<DailyFollowerCount> listCounts = dailyFollowerDaoService.getFollowerCounts(runner); // list of Daily objects
    List<Map<LocalDate,Integer>> dailyListMap = new ArrayList<>();
    for (DailyFollowerCount l : listCounts) {
      Map<LocalDate,Integer> newMap = new HashMap<LocalDate,Integer>();
      //newMap.put(l.getDate(),l.getNumberOfFollowers());
      dailyListMap.add(newMap);

    }
    return dailyListMap; // returns an arraylist of Hashmaps with LocalDate -> Integer

  }




  @GetMapping("/runCypress") // secret Cypress test endpoint
  @PreAuthorize("hasAnyAuthority('ADMIN','CONTRIBUTOR')")
  public ModelAndView runCypress() throws IOException, InterruptedException {
    GenerateCookies.RunCypress();
    System.out.println("cypress would be running");
    return new ModelAndView("redirect:/");
  }

  @RequestMapping("/getgraph")
  public String showHome(){

    return "graph";
  }

  @RequestMapping("/linechartdata")
  @ResponseBody
  public String getDataFromDB(@RequestParam String name){
    Runner runner = runnersDaoService.getSingleRunner(name);
    List<DailyFollowerCount> listCounts = dailyFollowerDaoService.getFollowerCounts(runner); // list of Daily objects

    JsonArray jsonDate = new JsonArray();
    JsonArray jsonFollowers = new JsonArray();
    JsonObject json = new JsonObject();
    listCounts.forEach(data->{
          jsonDate.add(data.getDate().toString());
          jsonFollowers.add(data.getNumberOfFollowers());
        }
    );

    json.add("date",jsonDate);
    json.add("followers", jsonFollowers);
    return json.toString();

  }


}
