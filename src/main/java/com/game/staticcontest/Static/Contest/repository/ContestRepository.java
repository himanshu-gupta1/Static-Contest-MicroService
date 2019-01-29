package com.game.staticcontest.Static.Contest.repository;

import com.game.staticcontest.Static.Contest.entity.Contest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends CrudRepository<Contest, String> {


}
