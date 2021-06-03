package com.patrick.Runners.runner;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;

import com.google.common.annotations.VisibleForTesting;

@Entity
@Transactional
public class DailyFollowerCount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="daily_instagram_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name="runner_id")
  private Runner runner;
  @Column(columnDefinition="DATE")
  private LocalDate date;
  private int numberOfFollowers;

  public DailyFollowerCount() {
  }

  public DailyFollowerCount(Runner runner, int numberOfFollowers) {
    this.runner = runner;
    this.numberOfFollowers = numberOfFollowers;
    this.date = LocalDate.now();
  }

  @VisibleForTesting
  public DailyFollowerCount(Runner runner, int numberOfFollowers, LocalDate date) {
    this.runner = runner;
    this.numberOfFollowers = numberOfFollowers;
    this.date = date;
  }

  public Runner getRunner() {
    return runner;
  }

  public void setRunner(Runner runner) {
    this.runner = runner;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public int getNumberOfFollowers() {
    return numberOfFollowers;
  }

  public void setNumberOfFollowers(int numberOfFollowers) {
    this.numberOfFollowers = numberOfFollowers;
  }

  public static void main(String[] args){
   Runner runner = new Runner("bob", "joe", "id");
   DailyFollowerCount firstCount = new DailyFollowerCount(runner, 100);
    System.out.println(firstCount.date);
    System.out.println(firstCount.numberOfFollowers);

  }



}
