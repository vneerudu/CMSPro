package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.TaskComments;
import com.cmspro.base.microservice.repository.TaskCommentsRepository;
import com.cmspro.base.microservice.repository.search.TaskCommentsSearchRepository;
import com.cmspro.base.microservice.service.dto.TaskCommentsDTO;
import com.cmspro.base.microservice.service.mapper.TaskCommentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TaskComments}.
 */
@Service
public class TaskCommentsService {

    private final Logger log = LoggerFactory.getLogger(TaskCommentsService.class);

    private final TaskCommentsRepository taskCommentsRepository;

    private final TaskCommentsMapper taskCommentsMapper;

    private final TaskCommentsSearchRepository taskCommentsSearchRepository;

    public TaskCommentsService(TaskCommentsRepository taskCommentsRepository, TaskCommentsMapper taskCommentsMapper, TaskCommentsSearchRepository taskCommentsSearchRepository) {
        this.taskCommentsRepository = taskCommentsRepository;
        this.taskCommentsMapper = taskCommentsMapper;
        this.taskCommentsSearchRepository = taskCommentsSearchRepository;
    }

    /**
     * Save a taskComments.
     *
     * @param taskCommentsDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskCommentsDTO save(TaskCommentsDTO taskCommentsDTO) {
        log.debug("Request to save TaskComments : {}", taskCommentsDTO);
        TaskComments taskComments = taskCommentsMapper.toEntity(taskCommentsDTO);
        taskComments = taskCommentsRepository.save(taskComments);
        TaskCommentsDTO result = taskCommentsMapper.toDto(taskComments);
        taskCommentsSearchRepository.save(taskComments);
        return result;
    }

    /**
     * Get all the taskComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskCommentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskComments");
        return taskCommentsRepository.findAll(pageable)
            .map(taskCommentsMapper::toDto);
    }


    /**
     * Get one taskComments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<TaskCommentsDTO> findOne(String id) {
        log.debug("Request to get TaskComments : {}", id);
        return taskCommentsRepository.findById(id)
            .map(taskCommentsMapper::toDto);
    }

    /**
     * Delete the taskComments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete TaskComments : {}", id);
        taskCommentsRepository.deleteById(id);
        taskCommentsSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskComments corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskCommentsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskComments for query {}", query);
        return taskCommentsSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskCommentsMapper::toDto);
    }
}
