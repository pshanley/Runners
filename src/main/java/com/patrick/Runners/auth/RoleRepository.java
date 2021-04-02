package com.patrick.Runners.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.patrick.Runners.teams.Team;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query("SELECT r FROM Role r where r.name = ?1")
  Role findByRoleName(String name);

}
