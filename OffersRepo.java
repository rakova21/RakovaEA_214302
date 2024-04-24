package com.crewrisk.repo;

import com.crewrisk.model.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepo extends JpaRepository<Offers, Long> {
}
