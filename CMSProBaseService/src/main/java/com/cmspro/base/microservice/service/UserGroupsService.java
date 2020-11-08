package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.UserGroups;
import com.cmspro.base.microservice.repository.UserGroupsRepository;
import com.cmspro.base.microservice.repository.search.UserGroupsSearchRepository;
import com.cmspro.base.microservice.service.dto.UserGroupsDTO;
import com.cmspro.base.microservice.service.mapper.UserGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UserGroups}.
 */
@Service
public class UserGroupsService {

    private final Logger log = LoggerFactory.getLogger(UserGroupsService.class);

    private final UserGroupsRepository userGroupsRepository;

    private final UserGroupsMapper userGroupsMapper;

    private final UserGroupsSearchRepository userGroupsSearchRepository;

    public UserGroupsService(UserGroupsRepository userGroupsRepository, UserGroupsMapper userGroupsMapper, UserGroupsSearchRepository userGroupsSearchRepository) {
        this.userGroupsRepository = userGroupsRepository;
        this.userGroupsMapper = userGroupsMapper;
        this.userGroupsSearchRepository = userGroupsSearchRepository;
    }

    /**
     * Save a userGroups.
     *
     * @param userGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    public UserGroupsDTO save(UserGroupsDTO userGroupsDTO) {
        log.debug("Request to save UserGroups : {}", userGroupsDTO);
        UserGroups userGroups = userGroupsMapper.toEntity(userGroupsDTO);
        userGroups = userGroupsRepository.save(userGroups);
        UserGroupsDTO result = userGroupsMapper.toDto(userGroups);
        userGroupsSearchRepository.save(userGroups);
        return result;
    }

    /**
     * Get all the userGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserGroups");
        return userGroupsRepository.findAll(pageable)
            .map(userGroupsMapper::toDto);
    }


    /**
     * Get one userGroups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UserGroupsDTO> findOne(String id) {
        log.debug("Request to get UserGroups : {}", id);
        return userGroupsRepository.findById(id)
            .map(userGroupsMapper::toDto);
    }

    /**
     * Delete the userGroups by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete UserGroups : {}", id);
        userGroupsRepository.deleteById(id);
        userGroupsSearchRepository.deleteById(id);
    }

    /**
     * Search for the userGroups corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserGroupsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserGroups for query {}", query);
        return userGroupsSearchRepository.search(queryStringQuery(query), pageable)
            .map(userGroupsMapper::toDto);
    }
}
