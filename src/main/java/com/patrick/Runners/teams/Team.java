package com.patrick.Runners.teams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import com.patrick.Runners.runner.Runner;

@Entity
@Transactional
@Table(name="teams")
public class Team implements Serializable {

  @Id
  @Column(name="team_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name="team_name")
  private String teamName;


  @OneToMany(
      mappedBy="team", // reference field in Runner
      cascade = {CascadeType.MERGE}, // Updating a team will update runners in the DB
      orphanRemoval = false) // removing team will not remove runner
  @Column(name="athletes")
  private List<Runner> athletes = new ArrayList<Runner>();


  public void addAthletes(Runner runner){
    athletes.add(runner);
  }

  public Team(){}

  public Team(String teamName) {

    this.teamName = teamName;
  }

  public List<Runner> getAthletes() {
    return athletes;
  }

  public void setAthletes(List<Runner> athletes) {
    this.athletes = athletes;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public String getTeamName() {

    return teamName;
  }



}
