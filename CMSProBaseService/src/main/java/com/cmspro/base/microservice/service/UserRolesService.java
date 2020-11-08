package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.UserRoles;
import com.cmspro.base.microservice.repository.UserRolesRepository;
import com.cmspro.base.microservice.repository.search.UserRolesSearchRepository;
import com.cmspro.base.microservice.service.dto.UserRolesDTO;
import com.cmspro.base.microservice.service.mapper.UserRolesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UserRoles}.
 */
@Service
public class UserRolesService {

    private final Logger log = LoggerFactory.getLogger(UserRolesService.class);

    private final UserRolesRepository userRolesRepository;

    private final UserRolesMapper userRolesMapper;

    private final UserRolesSearchRepository userRolesSearchRepository;

    public UserRolesService(UserRolesRepository userRolesRepository, UserRolesMapper userRolesMapper, UserRolesSearchRepository userRolesSearchRepository) {
        this.userRolesRepository = userRolesRepository;
        this.userRolesMapper = userRolesMapper;
        this.userRolesSearchRepository = userRolesSearchRepository;
    }

    /**
     * Save a userRoles.
     *
     * @param userRolesDTO the entity to save.
     * @return the persisted entity.
     */
    public UserRolesDTO save(UserRolesDTO userRolesDTO) {
        log.debug("Request to save UserRoles : {}", userRolesDTO);
        UserRoles userRoles = userRolesMapper.toEntity(userRolesDTO);
        userRoles = userRolesRepository.save(userRoles);
        UserRolesDTO result = userRolesMapper.toDto(userRoles);
        userRolesSearchRepository.save(userRoles);
        return result;
    }

    /**
     * Get all the userRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserRolesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserRoles");
        return userRolesRepository.findAll(pageable)
            .map(userRolesMapper::toDto);
    }


    /**
     * Get one userRoles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UserRolesDTO> findOne(String id) {
        log.debug("Request to get UserRoles : {}", id);
        return userRolesRepository.findById(id)
            .map(userRolesMapper::toDto);
    }

    /**
     * Delete the userRoles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete UserRoles : {}", id);
        userRolesRepository.deleteById(id);
        userRolesSearchRepository.deleteById(id);
    }

    /**
     * Search for the userRoles corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserRolesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserRoles for query {}", query);
        return userRolesSearchRepository.search(queryStringQuery(query), pageable)
            .map(userRolesMapper::toDto);
    }
}
