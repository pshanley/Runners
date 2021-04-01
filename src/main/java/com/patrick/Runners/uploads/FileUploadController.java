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

import com.patrick.Runners.teams.Team;
import com.patrick.Runners.teams.TeamDaoService;

@RestController
public class FileUploadController {

  @Autowired
  FileUploadService fileUploadService;

  TeamDaoService teamDaoService = new TeamDaoService();


  @PostMapping("/uploadFile")
  public ModelAndView uploadFile(@RequestParam(name="file") MultipartFile file, @RequestParam("teamName") String teamName) throws IOException {
    System.out.println("THIS IS THE FILE TYPE: " + file.getContentType());
    System.out.println("THIS IS THE FILE RESOURCE: " + file.getResource());
    String fileExtension= new String();
    String[] allowedExtensions = {".png",".jgp",".jpeg"};
    List<String> extensionsList = Arrays.asList(allowedExtensions);

    ModelAndView modelAndView = new ModelAndView();
    Team team = teamDaoService.getSingleTeam(teamName);

    if(!file.getOriginalFilename().isEmpty()){
       fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
      if(extensionsList.contains(fileExtension) && file.getSize()>0){
        fileUploadService.TeamFileUpload(file, team.getTeamName());
      }else{
        modelAndView.addObject("error", "The File must be one of the following: .jpg, .jpeg, .png");
      }
    }else{
      modelAndView.addObject("error","Please Choose a File");
    }



    modelAndView.setViewName("/teams/team");
    modelAndView.addObject("team", team);
    return modelAndView;


  }
}
