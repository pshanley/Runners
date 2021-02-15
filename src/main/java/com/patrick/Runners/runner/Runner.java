package com.patrick.Runners.runner;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Runner implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  private String instagramHandle;
  private String username; //FIRST_LAST
  private int followersCount;
  private String imageURL;


  public Runner() {
  }

  public Runner(String firstName, String lastName, String instagramHandle) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.instagramHandle = instagramHandle;
    this.username = firstName.toUpperCase() + "_" + lastName.toUpperCase();
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
}
