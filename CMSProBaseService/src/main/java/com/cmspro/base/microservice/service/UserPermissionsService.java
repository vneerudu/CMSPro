package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.UserPermissions;
import com.cmspro.base.microservice.repository.UserPermissionsRepository;
import com.cmspro.base.microservice.repository.search.UserPermissionsSearchRepository;
import com.cmspro.base.microservice.service.dto.UserPermissionsDTO;
import com.cmspro.base.microservice.service.mapper.UserPermissionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UserPermissions}.
 */
@Service
public class UserPermissionsService {

    private final Logger log = LoggerFactory.getLogger(UserPermissionsService.class);

    private final UserPermissionsRepository userPermissionsRepository;

    private final UserPermissionsMapper userPermissionsMapper;

    private final UserPermissionsSearchRepository userPermissionsSearchRepository;

    public UserPermissionsService(UserPermissionsRepository userPermissionsRepository, UserPermissionsMapper userPermissionsMapper, UserPermissionsSearchRepository userPermissionsSearchRepository) {
        this.userPermissionsRepository = userPermissionsRepository;
        this.userPermissionsMapper = userPermissionsMapper;
        this.userPermissionsSearchRepository = userPermissionsSearchRepository;
    }

    /**
     * Save a userPermissions.
     *
     * @param userPermissionsDTO the entity to save.
     * @return the persisted entity.
     */
    public UserPermissionsDTO save(UserPermissionsDTO userPermissionsDTO) {
        log.debug("Request to save UserPermissions : {}", userPermissionsDTO);
        UserPermissions userPermissions = userPermissionsMapper.toEntity(userPermissionsDTO);
        userPermissions = userPermissionsRepository.save(userPermissions);
        UserPermissionsDTO result = userPermissionsMapper.toDto(userPermissions);
        userPermissionsSearchRepository.save(userPermissions);
        return result;
    }

    /**
     * Get all the userPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserPermissionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPermissions");
        return userPermissionsRepository.findAll(pageable)
            .map(userPermissionsMapper::toDto);
    }


    /**
     * Get one userPermissions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UserPermissionsDTO> findOne(String id) {
        log.debug("Request to get UserPermissions : {}", id);
        return userPermissionsRepository.findById(id)
            .map(userPermissionsMapper::toDto);
    }

    /**
     * Delete the userPermissions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete UserPermissions : {}", id);
        userPermissionsRepository.deleteById(id);
        userPermissionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the userPermissions corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserPermissionsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserPermissions for query {}", query);
        return userPermissionsSearchRepository.search(queryStringQuery(query), pageable)
            .map(userPermissionsMapper::toDto);
    }
}
