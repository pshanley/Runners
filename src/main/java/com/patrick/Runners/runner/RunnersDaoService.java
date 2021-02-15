package com.patrick.Runners.runner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class RunnersDaoService {

  @Autowired
  private static RunnerRepository runnerRepository;

  public static void main(String[]  args){
      Runner runner = new Runner("patrick","h","instagram");
      addRunner(runner);

  }

  public static void addRunner(Runner runner){
    System.out.println(runner.getUsername());
    runnerRepository.save(runner);
  }







  public List<Runner> getRunnersList() {
    List<Runner> listRunners = new ArrayList<>();

    listRunners.add(new Runner("Drew", "Hunter", "drewhunter00"));
    listRunners.add(new Runner("Mo", "Farah", "gomofarah"));
    listRunners.add(new Runner("Galen", "Rupp", "galen_goat"));

    return listRunners;
  }


}
