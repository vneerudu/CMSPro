package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.TaskAttachmentOthers;
import com.cmspro.base.microservice.repository.TaskAttachmentOthersRepository;
import com.cmspro.base.microservice.repository.search.TaskAttachmentOthersSearchRepository;
import com.cmspro.base.microservice.service.dto.TaskAttachmentOthersDTO;
import com.cmspro.base.microservice.service.mapper.TaskAttachmentOthersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TaskAttachmentOthers}.
 */
@Service
public class TaskAttachmentOthersService {

    private final Logger log = LoggerFactory.getLogger(TaskAttachmentOthersService.class);

    private final TaskAttachmentOthersRepository taskAttachmentOthersRepository;

    private final TaskAttachmentOthersMapper taskAttachmentOthersMapper;

    private final TaskAttachmentOthersSearchRepository taskAttachmentOthersSearchRepository;

    public TaskAttachmentOthersService(TaskAttachmentOthersRepository taskAttachmentOthersRepository, TaskAttachmentOthersMapper taskAttachmentOthersMapper, TaskAttachmentOthersSearchRepository taskAttachmentOthersSearchRepository) {
        this.taskAttachmentOthersRepository = taskAttachmentOthersRepository;
        this.taskAttachmentOthersMapper = taskAttachmentOthersMapper;
        this.taskAttachmentOthersSearchRepository = taskAttachmentOthersSearchRepository;
    }

    /**
     * Save a taskAttachmentOthers.
     *
     * @param taskAttachmentOthersDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskAttachmentOthersDTO save(TaskAttachmentOthersDTO taskAttachmentOthersDTO) {
        log.debug("Request to save TaskAttachmentOthers : {}", taskAttachmentOthersDTO);
        TaskAttachmentOthers taskAttachmentOthers = taskAttachmentOthersMapper.toEntity(taskAttachmentOthersDTO);
        taskAttachmentOthers = taskAttachmentOthersRepository.save(taskAttachmentOthers);
        TaskAttachmentOthersDTO result = taskAttachmentOthersMapper.toDto(taskAttachmentOthers);
        taskAttachmentOthersSearchRepository.save(taskAttachmentOthers);
        return result;
    }

    /**
     * Get all the taskAttachmentOthers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskAttachmentOthersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskAttachmentOthers");
        return taskAttachmentOthersRepository.findAll(pageable)
            .map(taskAttachmentOthersMapper::toDto);
    }


    /**
     * Get one taskAttachmentOthers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<TaskAttachmentOthersDTO> findOne(String id) {
        log.debug("Request to get TaskAttachmentOthers : {}", id);
        return taskAttachmentOthersRepository.findById(id)
            .map(taskAttachmentOthersMapper::toDto);
    }

    /**
     * Delete the taskAttachmentOthers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete TaskAttachmentOthers : {}", id);
        taskAttachmentOthersRepository.deleteById(id);
        taskAttachmentOthersSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskAttachmentOthers corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskAttachmentOthersDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskAttachmentOthers for query {}", query);
        return taskAttachmentOthersSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskAttachmentOthersMapper::toDto);
    }
}
