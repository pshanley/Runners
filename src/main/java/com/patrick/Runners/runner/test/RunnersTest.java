package com.patrick.Runners.runner.test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.patrick.Runners.RunnersApplication;
import com.patrick.Runners.controllers.RunnersController;
import com.patrick.Runners.instagram.GetInstagramDetails;
import com.patrick.Runners.runner.DailyFollowerCount;
import com.patrick.Runners.runner.DailyFollowerDaoService;
import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnerRepository;
import com.patrick.Runners.runner.RunnersDaoService;
import com.patrick.Runners.teams.Team;
import com.patrick.Runners.teams.TeamDaoService;

public class RunnersTest {


  @Test
  public void getSingleRunner(){
    String username = "MO_FARAH";

    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnerRepository runnerRepository = configurableApplicationContext.getBean(RunnerRepository.class);

    Runner runner = runnerRepository.findByUsername(username);
    //List<Runner> runnersList = runnerRepository.findAll();

    System.out.println(runner.getInstagramHandle());
  }

  @Test
  public void getAllRunnersNotOnTeam(){
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnersDaoService runnersDaoService = configurableApplicationContext.getBean(RunnersDaoService.class);
    TeamDaoService teamDaoService = configurableApplicationContext.getBean(TeamDaoService.class);
    Team team = teamDaoService.getSingleTeam("Nike Oregon Project");

    List<Runner> runnerListNotOnNop = runnersDaoService.getAllRunnersNotOnTeam(team);
    for(Runner r: runnerListNotOnNop){
      System.out.println(r.getUsername());
    }
  }

  @Test
  public void getRunnerByInstagramHandle() {
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnersDaoService runnersDaoService = configurableApplicationContext.getBean(RunnersDaoService.class);
    //RunnerRepository repo = configurableApplicationContext.getBean(RunnerRepository.class);
    Runner runner = runnersDaoService.getRunnerByInstagramHandle("gomofarah");
    System.out.println(runner.getUsername());
  }

  @Test
  public void addDailyInstagramCount(){
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnersDaoService runnersDaoService = configurableApplicationContext.getBean(RunnersDaoService.class);
    DailyFollowerDaoService dailyFollowerDaoService = configurableApplicationContext.getBean(DailyFollowerDaoService.class);
    Runner runner = runnersDaoService.getSingleRunner("MO_FARAH");
    DailyFollowerCount dailyFollowerCount = new DailyFollowerCount(runner,223456);
    dailyFollowerDaoService.saveFollowerCount(dailyFollowerCount);
  }

  @Test
  public void getMapOfInstagramCounts(){
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnersDaoService runnersDaoService = configurableApplicationContext.getBean(RunnersDaoService.class);
    DailyFollowerDaoService dailyFollowerDaoService = configurableApplicationContext.getBean(DailyFollowerDaoService.class);
    Runner runner = runnersDaoService.getSingleRunner("MO_FARAH");
    List<DailyFollowerCount> listCounts = dailyFollowerDaoService.getFollowerCounts(runner);
    for(DailyFollowerCount d: listCounts){
      System.out.println(d.getRunner().getUsername() + ": " + d.getDate() + " - " + d.getNumberOfFollowers());
    }
  }

  @Test
  public void makeInstaRequestOffAllUsers() throws IOException, InterruptedException {
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnersDaoService runnersDaoService = configurableApplicationContext.getBean(RunnersDaoService.class);
    DailyFollowerDaoService dailyFollowerDaoService = configurableApplicationContext.getBean(DailyFollowerDaoService.class);
    List<Runner> runnersList = RunnersDaoService.getAllRunners();
    for(Runner r: runnersList){
      if(r.getInstagramHandle() != null){
        System.out.println(r.getUsername());
        int numberOfFollowers = GetInstagramDetails.getInstagramDetails(r);
        System.out.println(r.getUsername() +": " + numberOfFollowers);
        DailyFollowerCount dailyFollowerCount = new DailyFollowerCount(r, numberOfFollowers);
        dailyFollowerDaoService.saveFollowerCount(dailyFollowerCount);
      }
    }
  }




  @Test// generates fake instagram follower data between 2 specified dates for all runners
  public void generateFakeInstagramDailyData() throws IOException, InterruptedException {
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnersDaoService runnersDaoService = configurableApplicationContext.getBean(RunnersDaoService.class);
    DailyFollowerDaoService dailyFollowerDaoService = configurableApplicationContext.getBean(DailyFollowerDaoService.class);
    List<Runner> runnersList = RunnersDaoService.getAllRunners();

    String s = "2021-05-08";
    String e = "2021-05-31";
    LocalDate start = LocalDate.parse(s);
    LocalDate end = LocalDate.parse(e);
    List<LocalDate> totalDates = new ArrayList<>();
    while (!start.isAfter(end)) {
      totalDates.add(start);
      start = start.plusDays(1);
    }

    for(LocalDate l: totalDates){
      Random generator = new Random();
      int number = generator.nextInt(10);
      double multiplier = 0.98 + (number/100.0);
      for(Runner r: runnersList){
        System.out.println(l + ": " + r.getUsername() + " - " + (int)(r.getFollowersCount()*multiplier));
        DailyFollowerCount dailyFollowerCount = new DailyFollowerCount(r,(int)(r.getFollowersCount()*multiplier), l);
        dailyFollowerDaoService.saveFollowerCount(dailyFollowerCount);
      }
    }

  }

  @Test
  public void getMap(){

    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
    RunnersDaoService runnersDaoService = configurableApplicationContext.getBean(RunnersDaoService.class);
    DailyFollowerDaoService dailyFollowerDaoService = configurableApplicationContext.getBean(DailyFollowerDaoService.class);
    Runner runner = runnersDaoService.getSingleRunner("MO_FARAH");
    RunnersController controller = new RunnersController();
    List<Map<LocalDate,Integer>> map = controller.getMapOfDailyFollowers(runner);

  }

  @Test
  public void NumberOfPages(){
    // less runners than paging size, should be 1 page
    int numberOfPages = RunnersDaoService.getNumberOfPages(10,20);
    Assert.assertEquals(1,numberOfPages);

    // same number of runners as page size, should be 1
     numberOfPages = RunnersDaoService.getNumberOfPages(20,20);
    Assert.assertEquals(1,numberOfPages);

    // more runners than page size
    numberOfPages = RunnersDaoService.getNumberOfPages(41,20);
    Assert.assertEquals(3,numberOfPages);

  }

  @Test
  public void currentRunners(){
    String currentRunners = RunnersDaoService.getCurrentRunners(9, 0, 10);
    Assert.assertEquals("1 - 9", currentRunners);

    currentRunners = RunnersDaoService.getCurrentRunners(9, 0, 5);
    Assert.assertEquals("1 - 5", currentRunners);

    currentRunners = RunnersDaoService.getCurrentRunners(9, 1, 5);
    Assert.assertEquals("6 - 9", currentRunners);

    currentRunners = RunnersDaoService.getCurrentRunners(15, 1, 5);
    Assert.assertEquals("6 - 10", currentRunners);

  }




}
