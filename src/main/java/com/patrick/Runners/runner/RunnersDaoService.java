package com.patrick.Runners.runner;

import java.util.ArrayList;
import java.util.List;
import com.patrick.Runners.teams.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service // this creates a Singleton
public class RunnersDaoService {

  private ApplicationContext context;

  @Autowired
  public static RunnerRepository repo;

  public RunnersDaoService() {
  }

  @Autowired // the "repo" in savedRunner was null without the Autowired
  public RunnersDaoService(ApplicationContext context) {
    this.context = context;
    this.repo = context.getBean(RunnerRepository.class);
  }

  public long getNumberOfRunners(){
    return repo.count();
  }

  public static int getNumberOfPages(long numberOfRunners, int pageSize){
    int numberOfPages = (int)numberOfRunners/pageSize;
    int modulus = (int) numberOfRunners % pageSize;
    if(numberOfPages == 0){
      numberOfPages=1;
    } else if( modulus > 0){
      numberOfPages = numberOfPages + 1;
    }
    return numberOfPages;
  }

  public static String getCurrentRunners(long numberOfRunners, int pageNo, int pageSize ){
    int lastRunner = (pageNo + 1) * pageSize;
    if( lastRunner > numberOfRunners){
      lastRunner = (int)numberOfRunners;
    }

    int firstRunner = (pageNo * pageSize) + 1;

    String currentRunners = String.valueOf(firstRunner) + " - " + String.valueOf(lastRunner);
    return currentRunners;

  }

  public static void saveRunner(Runner runner){
    System.out.println("tryint to store a runner to RDBMS");
    repo.save(runner);
    System.out.println("saved the following Runner to RDBMS: " + runner.getUsername());
  }



  public List<Runner> getRunnersPaged(Integer pageNo, Integer pageSize, String sortBy) {
    if(pageSize == -1){
      pageSize = 100;
    }
    Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(Sort.Order.desc(sortBy)));
    Page<Runner> pagedResult = repo.findAll(paging);

    if(pagedResult.hasContent()) {
      return pagedResult.getContent();
    } else {
      return new ArrayList<Runner>();
    }
  }

  public static List<Runner> getAllRunners(){
    List<Runner> listRunners = repo.findAll();
    return listRunners;
  }

  public Runner getSingleRunner(String username){
    Runner runner = repo.findByUsername(username);
    return runner;
  }

  public void deleteRunner(Runner runner){
    repo.delete(runner);
  }

  public Runner getRunnerByInstagramHandle(String instagramHandle){
    Runner runner = repo.findByInstagramHandle(instagramHandle);
    return runner;
  }


  public static List<Runner> getAllRunnersNotOnTeam(Team team) {
    List<Runner> runnersList = getAllRunners();
    List<Runner> runnersToRemove = new ArrayList<>(); // can't change the size of a List during for a loop
    for (Runner r : runnersList) {
      if (r.getTeam() != null ) {  // getting a NPE below if a runner wasnt on a team, don't use .equals()?
        if (r.getTeam().getTeamName().equals(team.getTeamName())) {// The Team object that I pass in is NOT the same as the Team object that the athletes reference, but both Teams have the same teamName
          runnersToRemove.add(r);
        }
      }
    }
    for(Runner r : runnersToRemove){
      runnersList.remove(r);
    }

    return runnersList;
  }

  public static List<Runner> getAllRunnersNotOnLocalTeam(Team team) {
    List<Runner> runnersList = getAllRunners();
    List<Runner> runnersToRemove = new ArrayList<>(); // can't change the size of a List during for a loop
    List<Runner> localRunnersList = team.getAthletes();
    List<String> localRunnersUsernames = new ArrayList<>();

    for(Runner r: localRunnersList){
      localRunnersUsernames.add(r.getUsername());
    }

    for (Runner r: runnersList){
      if(localRunnersUsernames.contains(r.getUsername())){
        runnersToRemove.add(r);
      }
    }

    for (Runner r: runnersToRemove){
      runnersList.remove(r);
    }

    return runnersList;

  }
}
