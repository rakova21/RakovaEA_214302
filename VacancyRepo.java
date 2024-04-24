package com.crewrisk.repo;

import com.crewrisk.model.Vacancy;
import com.crewrisk.model.enums.VacancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findAllByStatus(VacancyStatus status);

    Vacancy findByJob_IdAndUser_Id(Long jobId, Long userId);
}
