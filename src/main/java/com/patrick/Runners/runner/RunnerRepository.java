package com.patrick.Runners.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RunnerRepository extends CrudRepository<Runner, Long> {
  List<Runner> findAll();

  @Query("SELECT r FROM Runner r where r.username = ?1")
  Runner findByUsername(String username);

  @Query("SELECT r FROM Runner r where r.instagramHandle = ?1")
  Runner findByInstagramHandle(String instagramHandle);


}
