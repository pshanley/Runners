package com.patrick.Runners.controllers.test;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patrick.Runners.auth.User;

@RestController
@RequestMapping("admin/test")
public class AdminControllerTest {

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public String getAllStudents(){
    System.out.println("printing all students");
    return "This is an admin test";
  }
}
