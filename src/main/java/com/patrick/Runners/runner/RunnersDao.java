package com.patrick.Runners.runner;

import java.util.ArrayList;
import java.util.List;

public class RunnersDao {

  public List<Runner> getRunnersList() {
    List<Runner> listRunners = new ArrayList<>();

    listRunners.add(new Runner("Drew", "Hunter", "drewhunter00"));
    listRunners.add(new Runner("Mo", "Farah", "gomofarah"));
    listRunners.add(new Runner("Galen", "Rupp", "galen_goat"));

    return listRunners;
  }


}
