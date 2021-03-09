package com.patrick.Runners.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.patrick.Runners.auth.AuthDaoService;
import com.patrick.Runners.auth.Role;
import com.patrick.Runners.auth.User;
import com.patrick.Runners.auth.UserRoles;

@Controller
public class UserController {

  @GetMapping("/registerUserForm")
  public String showAddUserForm(Model model) {
    User user = new User();
    model.addAttribute("user",user);
    System.out.println("redirecting to addUser form");
    return "registerUser";
  }

  @PostMapping("/registerUser")
  public String submitAddUserForm(@ModelAttribute("user") User user) throws IOException, InterruptedException {
    Role contributor_role = new Role(UserRoles.CONTRIBUTOR.name());
    Set<Role> contributor = new HashSet<Role>();
    contributor.add(contributor_role);


    user.setRoles(contributor);
    user.setEnabled(true);
    AuthDaoService.addNewUser(user);
    return "registeredUserSuccess";
  }


}
