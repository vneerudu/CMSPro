package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.TaskAttachments;
import com.cmspro.base.microservice.repository.TaskAttachmentsRepository;
import com.cmspro.base.microservice.repository.search.TaskAttachmentsSearchRepository;
import com.cmspro.base.microservice.service.dto.TaskAttachmentsDTO;
import com.cmspro.base.microservice.service.mapper.TaskAttachmentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TaskAttachments}.
 */
@Service
public class TaskAttachmentsService {

    private final Logger log = LoggerFactory.getLogger(TaskAttachmentsService.class);

    private final TaskAttachmentsRepository taskAttachmentsRepository;

    private final TaskAttachmentsMapper taskAttachmentsMapper;

    private final TaskAttachmentsSearchRepository taskAttachmentsSearchRepository;

    public TaskAttachmentsService(TaskAttachmentsRepository taskAttachmentsRepository, TaskAttachmentsMapper taskAttachmentsMapper, TaskAttachmentsSearchRepository taskAttachmentsSearchRepository) {
        this.taskAttachmentsRepository = taskAttachmentsRepository;
        this.taskAttachmentsMapper = taskAttachmentsMapper;
        this.taskAttachmentsSearchRepository = taskAttachmentsSearchRepository;
    }

    /**
     * Save a taskAttachments.
     *
     * @param taskAttachmentsDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskAttachmentsDTO save(TaskAttachmentsDTO taskAttachmentsDTO) {
        log.debug("Request to save TaskAttachments : {}", taskAttachmentsDTO);
        TaskAttachments taskAttachments = taskAttachmentsMapper.toEntity(taskAttachmentsDTO);
        taskAttachments = taskAttachmentsRepository.save(taskAttachments);
        TaskAttachmentsDTO result = taskAttachmentsMapper.toDto(taskAttachments);
        taskAttachmentsSearchRepository.save(taskAttachments);
        return result;
    }

    /**
     * Get all the taskAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskAttachmentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskAttachments");
        return taskAttachmentsRepository.findAll(pageable)
            .map(taskAttachmentsMapper::toDto);
    }


    /**
     * Get one taskAttachments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<TaskAttachmentsDTO> findOne(String id) {
        log.debug("Request to get TaskAttachments : {}", id);
        return taskAttachmentsRepository.findById(id)
            .map(taskAttachmentsMapper::toDto);
    }

    /**
     * Delete the taskAttachments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete TaskAttachments : {}", id);
        taskAttachmentsRepository.deleteById(id);
        taskAttachmentsSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskAttachments corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskAttachmentsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskAttachments for query {}", query);
        return taskAttachmentsSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskAttachmentsMapper::toDto);
    }
}
