package com.patrick.Runners.auth;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository("MySQL")
@Service
public class AuthDaoService implements UserDao{

  private final PasswordEncoder passwordEncoder;
  private static UserRepository userRepository;
  private static RoleRepository roleRepository;

  @Autowired
  public AuthDaoService(PasswordEncoder passwordEncoder,UserRepository userRepository, RoleRepository roleRepository ) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public Optional<User> selectApplicationUserByUsername(String username) {
    return getApplicationUsers()
        .stream()
        .filter(applicationUser -> username.equals(applicationUser.getUsername()))
        .findFirst();
  }


  public static void addNewUser(User user){
    userRepository.save(user);

  }

  public static void addRole(Role role){
    roleRepository.save(role);
  }
  // This runs when someone tries to login
  private List<User> getApplicationUsers(){
    List<User> applicationUsers =  userRepository.findAll();
    System.out.println("Found the following users: ");
    for(User user : applicationUsers){
      System.out.println(user.getUsername());
    }

    return applicationUsers;

  }
}
