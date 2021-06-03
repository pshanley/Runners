package com.patrick.Runners.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DailyFollowerDaoService {

  @Autowired
  public static DailyFollowerRepository dailyInstagramRepository;
  private ApplicationContext context;

  public DailyFollowerDaoService() {
  }

  @Autowired // the "repo" in savedRunner was null without the Autowired
  public DailyFollowerDaoService(ApplicationContext context) {
    this.context = context;
    this.dailyInstagramRepository = context.getBean(DailyFollowerRepository.class);
  }

  public void saveFollowerCount(DailyFollowerCount count){
    dailyInstagramRepository.save(count);
  }

  public List<DailyFollowerCount> getFollowerCounts(Runner runner){
    return dailyInstagramRepository.getListofFollowerCounts(runner);
  }

}
