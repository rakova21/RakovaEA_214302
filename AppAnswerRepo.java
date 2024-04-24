package com.crewrisk.repo;

import com.crewrisk.model.AppAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAnswerRepo extends JpaRepository<AppAnswer, Long> {
}
