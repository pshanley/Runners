package com.patrick.Runners.teams;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.patrick.Runners.runner.Runner;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
  List<Team> findAll();

  @Query("SELECT t FROM Team t where t.teamName = ?1")
  Team findByTeamName(String teamName);


}
