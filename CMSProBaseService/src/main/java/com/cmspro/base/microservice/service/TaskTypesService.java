package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.TaskTypes;
import com.cmspro.base.microservice.repository.TaskTypesRepository;
import com.cmspro.base.microservice.repository.search.TaskTypesSearchRepository;
import com.cmspro.base.microservice.service.dto.TaskTypesDTO;
import com.cmspro.base.microservice.service.mapper.TaskTypesMapper;
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
 * Service Implementation for managing {@link TaskTypes}.
 */
@Service
public class TaskTypesService {

    private final Logger log = LoggerFactory.getLogger(TaskTypesService.class);

    private final TaskTypesRepository taskTypesRepository;

    private final TaskTypesMapper taskTypesMapper;

    private final TaskTypesSearchRepository taskTypesSearchRepository;

    public TaskTypesService(TaskTypesRepository taskTypesRepository, TaskTypesMapper taskTypesMapper, TaskTypesSearchRepository taskTypesSearchRepository) {
        this.taskTypesRepository = taskTypesRepository;
        this.taskTypesMapper = taskTypesMapper;
        this.taskTypesSearchRepository = taskTypesSearchRepository;
    }

    /**
     * Save a taskTypes.
     *
     * @param taskTypesDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskTypesDTO save(TaskTypesDTO taskTypesDTO) {
        log.debug("Request to save TaskTypes : {}", taskTypesDTO);
        TaskTypes taskTypes = taskTypesMapper.toEntity(taskTypesDTO);
        taskTypes = taskTypesRepository.save(taskTypes);
        TaskTypesDTO result = taskTypesMapper.toDto(taskTypes);
        taskTypesSearchRepository.save(taskTypes);
        return result;
    }

    /**
     * Get all the taskTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskTypesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskTypes");
        return taskTypesRepository.findAll(pageable)
            .map(taskTypesMapper::toDto);
    }



    /**
     *  Get all the taskTypes where Task is {@code null}.
     *  @return the list of entities.
     */
    public List<TaskTypesDTO> findAllWhereTaskIsNull() {
        log.debug("Request to get all taskTypes where Task is null");
        return StreamSupport
            .stream(taskTypesRepository.findAll().spliterator(), false)
            .filter(taskTypes -> taskTypes.getTask() == null)
            .map(taskTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taskTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<TaskTypesDTO> findOne(String id) {
        log.debug("Request to get TaskTypes : {}", id);
        return taskTypesRepository.findById(id)
            .map(taskTypesMapper::toDto);
    }

    /**
     * Delete the taskTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete TaskTypes : {}", id);
        taskTypesRepository.deleteById(id);
        taskTypesSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskTypes corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskTypesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskTypes for query {}", query);
        return taskTypesSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskTypesMapper::toDto);
    }
}
