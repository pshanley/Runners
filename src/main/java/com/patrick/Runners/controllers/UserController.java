package com.patrick.Runners.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.patrick.Runners.auth.AuthDaoService;
import com.patrick.Runners.auth.Role;
import com.patrick.Runners.auth.RoleRepository;
import com.patrick.Runners.auth.User;
import com.patrick.Runners.auth.UserDTO;
import com.patrick.Runners.auth.UserRoles;

@Controller
public class UserController {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthDaoService authDaoService;

  @Autowired
  private RoleRepository roleRepository;

  @GetMapping("/registerUserForm")
  public String showAddUserForm(Model model) {
    UserDTO userDTO = new UserDTO();
    model.addAttribute("userDTO",userDTO);
    System.out.println("redirecting to addUser form");
    return "users/registerUser";
  }

  @PostMapping("/registerUser")
  public ModelAndView submitAddUserForm(@ModelAttribute("userDTO") UserDTO userDTO) throws IOException, InterruptedException {
    ModelAndView modelAndView = new ModelAndView();
    //Role contributor_role = new Role(UserRoles.CONTRIBUTOR.name()); // this iscreating a new RDBMS row each time a user is added
    //Set<Role> contributor = new HashSet<Role>();
    //contributor.add(contributor_role);

    String passwordError = passwordValidator(userDTO);
    if(!passwordError.equals("")){
      modelAndView.addObject("error",passwordError);
      modelAndView.setViewName("users/registerUser");
      return modelAndView;
    }

    Role contributor_role = roleRepository.findByRoleName(UserRoles.CONTRIBUTOR.name());
    Set<Role> contributor = new HashSet<Role>();
    contributor.add(contributor_role);

    User user = new User(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), contributor , true);
    AuthDaoService.addNewUser(user);

    modelAndView.setViewName("users/registeredUserSuccess");
    modelAndView.addObject("user",user);
    return modelAndView;

  }

  @RequestMapping("/users")
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public ModelAndView listUsers() {
    ModelAndView modelAndView = new ModelAndView();
    List<User> users = authDaoService.getApplicationUsers();
    modelAndView.addObject("users", users);
    modelAndView.setViewName("users/users");
    return modelAndView;
  }

  public String passwordValidator(UserDTO userDTO){
    String[] forbiddenCharacters = {"#", "%", "^", "*", "(", ")", "{" ,"}" ,"/" ,"<" ,">"};
    StringBuilder sb = new StringBuilder();
    for(String str : forbiddenCharacters){
      sb.append(str).append(" ");
    }
    String forbiddenCharactersString = sb.substring(0, sb.length() -1);

    Optional<User> userCheck = authDaoService.selectApplicationUserByUsername(userDTO.getUsername());
    if( !userDTO.getPassword().equals(userDTO.getMatchingPassword())){
     return "Passwords Do Not Match";
    } else if(userDTO.getPassword().length() < 8 ) {
      return "Passwords must have 8 or more characters";
    } else if(userDTO.getUsername().contains(" ")){
      return "Username can not contain a space";
    } else if( Arrays.stream(forbiddenCharacters).anyMatch(userDTO.getUsername()::contains)){

      return "Username can not contain the following characters".concat(": " + forbiddenCharactersString);
    }
    else if(userDTO.getUsername().length() < 1){
      return "Username can not be blank";
    } else if( authDaoService.selectApplicationUserByUsername(userDTO.getUsername()).isPresent() ){
      return "Username is taken";
    } else{
      return "";
    }
  }



}
