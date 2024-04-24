package com.crewrisk.repo;

import com.crewrisk.model.Primarys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimarysRepo extends JpaRepository<Primarys, Long> {
}
