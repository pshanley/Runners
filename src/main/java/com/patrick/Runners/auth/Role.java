package com.patrick.Runners.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // persistent object, pretty much means a Table
@Table(name="roles")
public class Role {

  @Id
  @Column(name="role_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY) // ID is generated automatically by hibernate
  private Integer id;

  private String name;

  public Role() {
  }

  public Role(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
