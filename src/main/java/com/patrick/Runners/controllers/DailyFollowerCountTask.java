package com.patrick.Runners.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.patrick.Runners.instagram.GetInstagramDetails;
import com.patrick.Runners.runner.DailyFollowerCount;
import com.patrick.Runners.runner.DailyFollowerDaoService;
import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnersDaoService;

@Component
public class DailyFollowerCountTask {

  DailyFollowerDaoService dailyFollowerDaoService = new DailyFollowerDaoService();

  @Scheduled(cron="0 1 0 * * ?") // Every day at this GMT
  public void storeDailyFollowerCount() throws IOException, InterruptedException {
    List<Runner> runnersList = RunnersDaoService.getRunnersList();
    for (Runner r : runnersList) {
      if (r.getInstagramHandle() != null && !r.getInstagramHandle().isEmpty()) {
        System.out.println(r.getUsername());
        int numberOfFollowers = GetInstagramDetails.getInstagramDetails(r);
        r.setFollowersCount(numberOfFollowers);
        System.out.println(r.getUsername() + ": " + numberOfFollowers);
        DailyFollowerCount dailyFollowerCount = new DailyFollowerCount(r, numberOfFollowers);
        dailyFollowerDaoService.saveFollowerCount(dailyFollowerCount);
        RunnersDaoService.saveRunner(r);
      }
    }
  }
}
