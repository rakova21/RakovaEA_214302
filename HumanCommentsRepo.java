package com.crewrisk.repo;

import com.crewrisk.model.HumanComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanCommentsRepo extends JpaRepository<HumanComments, Long> {
}
