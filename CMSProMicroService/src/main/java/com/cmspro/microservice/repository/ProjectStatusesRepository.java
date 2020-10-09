package com.cmspro.microservice.repository;

import com.cmspro.microservice.domain.ProjectStatuses;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectStatuses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectStatusesRepository extends JpaRepository<ProjectStatuses, Long> {
}
