package com.patrick.Runners.runner;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.patrick.Runners.auth.User;
import com.patrick.Runners.teams.Team;

//@TypeDef(name = "user", typeClass = User.class)
@Entity
@Transactional
public class Runner implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  private String instagramHandle;
  private String username; //FIRST_LAST
  private int followersCount;

  private String userWhoAdded; // don't feel like setting up a "User" type, will keep as a string for now

  @Column(name="imageurl", columnDefinition="BLOB")
  private String imageURL;



  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;

  public Runner() {
  }

  public Runner(String firstName, String lastName, String instagramHandle) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.instagramHandle = instagramHandle;
    this.username = firstName.toUpperCase() + "_" + lastName.toUpperCase();

  }


  public Runner(String firstName, String lastName, String instagramHandle, Team team) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.instagramHandle = instagramHandle;
    this.username = firstName.toUpperCase() + "_" + lastName.toUpperCase();
    this.team = team;

  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getInstagramHandle() {
    return instagramHandle;
  }

  public void setInstagramHandle(String instagramHandle) {
    this.instagramHandle = instagramHandle;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public int getFollowersCount(){
    return followersCount;
  }

  public void setFollowersCount(int followersCount){
    this.followersCount = followersCount;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public String getUserWhoAdded(){
    return userWhoAdded;
  }

  public void setUserWhoAdded(String user){
    this.userWhoAdded = user;
  }
}
