package com.patrick.Runners;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;


import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDaoService;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RunnersDaoTest {



  public final String firstName = "drew00";
  public final String lastName = "hunter00";
  public final String instagramHandle = "drewHunterInstagram";

  public final Runner runner = new Runner(firstName,lastName,instagramHandle);

  @Test
  public void username(){
    assertEquals("DREW00_HUNTER00", runner.getUsername());
    }

  @Test
  public void addRunnerToDB(){
    RunnersDaoService.addRunner(runner);

  }

}
