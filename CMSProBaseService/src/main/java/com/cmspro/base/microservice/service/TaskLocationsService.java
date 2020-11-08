package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.TaskLocations;
import com.cmspro.base.microservice.repository.TaskLocationsRepository;
import com.cmspro.base.microservice.repository.search.TaskLocationsSearchRepository;
import com.cmspro.base.microservice.service.dto.TaskLocationsDTO;
import com.cmspro.base.microservice.service.mapper.TaskLocationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TaskLocations}.
 */
@Service
public class TaskLocationsService {

    private final Logger log = LoggerFactory.getLogger(TaskLocationsService.class);

    private final TaskLocationsRepository taskLocationsRepository;

    private final TaskLocationsMapper taskLocationsMapper;

    private final TaskLocationsSearchRepository taskLocationsSearchRepository;

    public TaskLocationsService(TaskLocationsRepository taskLocationsRepository, TaskLocationsMapper taskLocationsMapper, TaskLocationsSearchRepository taskLocationsSearchRepository) {
        this.taskLocationsRepository = taskLocationsRepository;
        this.taskLocationsMapper = taskLocationsMapper;
        this.taskLocationsSearchRepository = taskLocationsSearchRepository;
    }

    /**
     * Save a taskLocations.
     *
     * @param taskLocationsDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskLocationsDTO save(TaskLocationsDTO taskLocationsDTO) {
        log.debug("Request to save TaskLocations : {}", taskLocationsDTO);
        TaskLocations taskLocations = taskLocationsMapper.toEntity(taskLocationsDTO);
        taskLocations = taskLocationsRepository.save(taskLocations);
        TaskLocationsDTO result = taskLocationsMapper.toDto(taskLocations);
        taskLocationsSearchRepository.save(taskLocations);
        return result;
    }

    /**
     * Get all the taskLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskLocationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskLocations");
        return taskLocationsRepository.findAll(pageable)
            .map(taskLocationsMapper::toDto);
    }



    /**
     *  Get all the taskLocations where Task is {@code null}.
     *  @return the list of entities.
     */
    public List<TaskLocationsDTO> findAllWhereTaskIsNull() {
        log.debug("Request to get all taskLocations where Task is null");
        return StreamSupport
            .stream(taskLocationsRepository.findAll().spliterator(), false)
            .filter(taskLocations -> taskLocations.getTask() == null)
            .map(taskLocationsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taskLocations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<TaskLocationsDTO> findOne(String id) {
        log.debug("Request to get TaskLocations : {}", id);
        return taskLocationsRepository.findById(id)
            .map(taskLocationsMapper::toDto);
    }

    /**
     * Delete the taskLocations by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete TaskLocations : {}", id);
        taskLocationsRepository.deleteById(id);
        taskLocationsSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskLocations corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskLocationsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskLocations for query {}", query);
        return taskLocationsSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskLocationsMapper::toDto);
    }
}
