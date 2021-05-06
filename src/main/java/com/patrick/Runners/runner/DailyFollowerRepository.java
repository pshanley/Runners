package com.patrick.Runners.runner;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyFollowerRepository extends CrudRepository<DailyFollowerCount, Long>
{
  @Query("SELECT r FROM DailyFollowerCount r where r.runner = :runner")
  List<DailyFollowerCount> getListofFollowerCounts(Runner runner);
}
