package com.patrick.Runners.runner;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contributor/test")
public class ContributorControllerTest {

  @GetMapping
  @PreAuthorize("hasAuthority('CONTRIBUTOR')")
  public String getAllStudents(){
    System.out.println("printing all students");
    return "You can see this as a contributor";
  }



}
