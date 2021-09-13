package com.cnu.spg.team.repository;

import com.cnu.spg.team.domain.TeamElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamElementRepository extends JpaRepository<TeamElement, Long>, TeamElementCustomRepository {
}
