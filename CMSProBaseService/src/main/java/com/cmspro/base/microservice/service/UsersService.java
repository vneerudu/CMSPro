package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Users;
import com.cmspro.base.microservice.repository.UsersRepository;
import com.cmspro.base.microservice.repository.search.UsersSearchRepository;
import com.cmspro.base.microservice.service.dto.UsersDTO;
import com.cmspro.base.microservice.service.mapper.UsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
public class UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersService.class);

    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    private final UsersSearchRepository usersSearchRepository;

    public UsersService(UsersRepository usersRepository, UsersMapper usersMapper, UsersSearchRepository usersSearchRepository) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.usersSearchRepository = usersSearchRepository;
    }

    /**
     * Save a users.
     *
     * @param usersDTO the entity to save.
     * @return the persisted entity.
     */
    public UsersDTO save(UsersDTO usersDTO) {
        log.debug("Request to save Users : {}", usersDTO);
        Users users = usersMapper.toEntity(usersDTO);
        users = usersRepository.save(users);
        UsersDTO result = usersMapper.toDto(users);
        usersSearchRepository.save(users);
        return result;
    }

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return usersRepository.findAll(pageable)
            .map(usersMapper::toDto);
    }


    /**
     * Get one users by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UsersDTO> findOne(String id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id)
            .map(usersMapper::toDto);
    }

    /**
     * Delete the users by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
        usersSearchRepository.deleteById(id);
    }

    /**
     * Search for the users corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UsersDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Users for query {}", query);
        return usersSearchRepository.search(queryStringQuery(query), pageable)
            .map(usersMapper::toDto);
    }
}
