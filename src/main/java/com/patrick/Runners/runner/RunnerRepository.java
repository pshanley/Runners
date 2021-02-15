package com.patrick.Runners.runner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface RunnerRepository extends CrudRepository<Runner, Long> {
  List<Runner> findByusername(String username);

  Runner findById(long id);
}
