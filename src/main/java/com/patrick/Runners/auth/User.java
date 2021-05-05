package com.patrick.Runners.auth;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name="users")
public class User {

  @Id
  @Column(name="user_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String username;
  private String password;
  private boolean enabled;

  @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name="user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles; // this has to be a List, Set, or collection

  public User() {
  }


  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public User(String username, String password, Set<Role> roles, boolean enabled) {
    this.password = password;
    this.username = username;
    this.roles = roles;
    this.enabled = enabled;
  }



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Set<Role> getRoles()
  {
    return roles;
  }
  public void setRoles(Set<Role> roles)
  {
    this.roles = roles;
  }
}
