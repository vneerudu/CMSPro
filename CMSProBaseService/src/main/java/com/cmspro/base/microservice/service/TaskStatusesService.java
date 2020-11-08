package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.TaskStatuses;
import com.cmspro.base.microservice.repository.TaskStatusesRepository;
import com.cmspro.base.microservice.repository.search.TaskStatusesSearchRepository;
import com.cmspro.base.microservice.service.dto.TaskStatusesDTO;
import com.cmspro.base.microservice.service.mapper.TaskStatusesMapper;
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
 * Service Implementation for managing {@link TaskStatuses}.
 */
@Service
public class TaskStatusesService {

    private final Logger log = LoggerFactory.getLogger(TaskStatusesService.class);

    private final TaskStatusesRepository taskStatusesRepository;

    private final TaskStatusesMapper taskStatusesMapper;

    private final TaskStatusesSearchRepository taskStatusesSearchRepository;

    public TaskStatusesService(TaskStatusesRepository taskStatusesRepository, TaskStatusesMapper taskStatusesMapper, TaskStatusesSearchRepository taskStatusesSearchRepository) {
        this.taskStatusesRepository = taskStatusesRepository;
        this.taskStatusesMapper = taskStatusesMapper;
        this.taskStatusesSearchRepository = taskStatusesSearchRepository;
    }

    /**
     * Save a taskStatuses.
     *
     * @param taskStatusesDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskStatusesDTO save(TaskStatusesDTO taskStatusesDTO) {
        log.debug("Request to save TaskStatuses : {}", taskStatusesDTO);
        TaskStatuses taskStatuses = taskStatusesMapper.toEntity(taskStatusesDTO);
        taskStatuses = taskStatusesRepository.save(taskStatuses);
        TaskStatusesDTO result = taskStatusesMapper.toDto(taskStatuses);
        taskStatusesSearchRepository.save(taskStatuses);
        return result;
    }

    /**
     * Get all the taskStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskStatusesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskStatuses");
        return taskStatusesRepository.findAll(pageable)
            .map(taskStatusesMapper::toDto);
    }



    /**
     *  Get all the taskStatuses where Task is {@code null}.
     *  @return the list of entities.
     */
    public List<TaskStatusesDTO> findAllWhereTaskIsNull() {
        log.debug("Request to get all taskStatuses where Task is null");
        return StreamSupport
            .stream(taskStatusesRepository.findAll().spliterator(), false)
            .filter(taskStatuses -> taskStatuses.getTask() == null)
            .map(taskStatusesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taskStatuses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<TaskStatusesDTO> findOne(String id) {
        log.debug("Request to get TaskStatuses : {}", id);
        return taskStatusesRepository.findById(id)
            .map(taskStatusesMapper::toDto);
    }

    /**
     * Delete the taskStatuses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete TaskStatuses : {}", id);
        taskStatusesRepository.deleteById(id);
        taskStatusesSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskStatuses corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskStatusesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskStatuses for query {}", query);
        return taskStatusesSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskStatusesMapper::toDto);
    }
}
