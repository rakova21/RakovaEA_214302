package com.crewrisk.repo;

import com.crewrisk.model.Users;
import com.crewrisk.model.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    List<Users> findAllByOrderByRole();

    List<Users> findAllByRoleAndTertiary_MaritalAndTertiary_OriginAndTertiary_Citizenship(Role role, Marital marital, Origin origin, Citizenship citizenship);

    List<Users> findAllByRole(Role role);

    List<Users> findAllByRoleAndTertiary_Marital(Role role, Marital marital);

    List<Users> findAllByRoleAndTertiary_Origin(Role role, Origin origin);

    List<Users> findAllByRoleAndTertiary_Citizenship(Role role, Citizenship citizenship);

    List<Users> findAllByRoleAndTertiary_Education(Role role, Education education);

    List<Users> findAllByRoleOrderBySecondary_Experience(Role role);
}
