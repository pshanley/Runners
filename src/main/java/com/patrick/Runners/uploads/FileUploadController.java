package com.patrick.Runners.uploads;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDaoService;
import com.patrick.Runners.teams.Team;
import com.patrick.Runners.teams.TeamDaoService;

@RestController
public class FileUploadController {

  @Autowired
  FileUploadService fileUploadService;

  TeamDaoService teamDaoService = new TeamDaoService();
  RunnersDaoService runnersDaoService = new RunnersDaoService();

  @PostMapping("/uploadFile") // File Uploads are not persistent across container restarts yet
  public ModelAndView uploadFile( @RequestParam(name = "file") MultipartFile file, @RequestParam("teamName") String teamName) throws IOException {

    ModelAndView modelAndView = new ModelAndView();
    Team team = teamDaoService.getSingleTeam(teamName);

    String fileUploadValidation = addFileUploadValidations(file);

    if (!fileUploadValidation.equals("")) {
      modelAndView.addObject("error", fileUploadValidation);
      modelAndView.setViewName("teams/editTeamForm");
      modelAndView.addObject("team",team);

      List<Runner> athletesNotOnTeam = RunnersDaoService.getAllRunnersNotOnTeam(team);
      modelAndView.addObject("athletesNotOnTeam", athletesNotOnTeam);
      return modelAndView;

    }

    fileUploadService.FileUpload(file, team.getTeamName());
    modelAndView.setViewName("/teams/team");
    modelAndView.addObject("team", team);
    return modelAndView;
  }

  @PostMapping("/uploadRunnerImage")
  public ModelAndView uploadRunnerImage(
      @RequestParam(name = "file") MultipartFile file, @RequestParam("runner") String runnerName) throws IOException {

    Runner runner = runnersDaoService.getSingleRunner(runnerName);
    ModelAndView modelAndView = new ModelAndView();

    String fileUploadValidation = addFileUploadValidations(file);

    if (!fileUploadValidation.equals("")) {
      modelAndView.addObject("error", fileUploadValidation);
      modelAndView.setViewName("runners/editRunner");
      modelAndView.addObject("runner", runner);
      List<Team> teamList = teamDaoService.getAllTeams();
      modelAndView.addObject("teams", teamList); // server-side validations are kind of sloppy :/ need to pass the same data back and forth to keep the same state
      return modelAndView;
    }

    fileUploadService.FileUpload(file, runnerName);
    //FileUploadService fileUploadService1 = new FileUploadService();
    //fileUploadService1.RunnerFileUpload(file,runnerName);
    modelAndView.setViewName("/runner");
    modelAndView.addObject("runner", runner);
    return modelAndView;
  }

  public static String addFileUploadValidations(MultipartFile file) {
    int maxFileSize = 10485760; // set no maximum file size in application.properties, handle it here
    String fileExtension = new String();
    String[] allowedExtensions = {".png", ".jpg", ".jpeg", ".PNG", ".JPG", ".JPEG"};
    List<String> extensionsList = Arrays.asList(allowedExtensions);

    if (file.getSize() > maxFileSize) {
      return "File must be less than 10 MB";
    } else if (file.getOriginalFilename().isEmpty()){
      return "Please chose a file AAA";
    } else {
      fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
    }
    if (!extensionsList.contains(fileExtension)){
      return "The File must be one of the following: .jpg, .jpeg, .png";
    }
      return "";

  }

}
