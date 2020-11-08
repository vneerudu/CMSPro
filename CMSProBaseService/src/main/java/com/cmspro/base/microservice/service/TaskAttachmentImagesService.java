package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.TaskAttachmentImages;
import com.cmspro.base.microservice.repository.TaskAttachmentImagesRepository;
import com.cmspro.base.microservice.repository.search.TaskAttachmentImagesSearchRepository;
import com.cmspro.base.microservice.service.dto.TaskAttachmentImagesDTO;
import com.cmspro.base.microservice.service.mapper.TaskAttachmentImagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TaskAttachmentImages}.
 */
@Service
public class TaskAttachmentImagesService {

    private final Logger log = LoggerFactory.getLogger(TaskAttachmentImagesService.class);

    private final TaskAttachmentImagesRepository taskAttachmentImagesRepository;

    private final TaskAttachmentImagesMapper taskAttachmentImagesMapper;

    private final TaskAttachmentImagesSearchRepository taskAttachmentImagesSearchRepository;

    public TaskAttachmentImagesService(TaskAttachmentImagesRepository taskAttachmentImagesRepository, TaskAttachmentImagesMapper taskAttachmentImagesMapper, TaskAttachmentImagesSearchRepository taskAttachmentImagesSearchRepository) {
        this.taskAttachmentImagesRepository = taskAttachmentImagesRepository;
        this.taskAttachmentImagesMapper = taskAttachmentImagesMapper;
        this.taskAttachmentImagesSearchRepository = taskAttachmentImagesSearchRepository;
    }

    /**
     * Save a taskAttachmentImages.
     *
     * @param taskAttachmentImagesDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskAttachmentImagesDTO save(TaskAttachmentImagesDTO taskAttachmentImagesDTO) {
        log.debug("Request to save TaskAttachmentImages : {}", taskAttachmentImagesDTO);
        TaskAttachmentImages taskAttachmentImages = taskAttachmentImagesMapper.toEntity(taskAttachmentImagesDTO);
        taskAttachmentImages = taskAttachmentImagesRepository.save(taskAttachmentImages);
        TaskAttachmentImagesDTO result = taskAttachmentImagesMapper.toDto(taskAttachmentImages);
        taskAttachmentImagesSearchRepository.save(taskAttachmentImages);
        return result;
    }

    /**
     * Get all the taskAttachmentImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskAttachmentImagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskAttachmentImages");
        return taskAttachmentImagesRepository.findAll(pageable)
            .map(taskAttachmentImagesMapper::toDto);
    }


    /**
     * Get one taskAttachmentImages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<TaskAttachmentImagesDTO> findOne(String id) {
        log.debug("Request to get TaskAttachmentImages : {}", id);
        return taskAttachmentImagesRepository.findById(id)
            .map(taskAttachmentImagesMapper::toDto);
    }

    /**
     * Delete the taskAttachmentImages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete TaskAttachmentImages : {}", id);
        taskAttachmentImagesRepository.deleteById(id);
        taskAttachmentImagesSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskAttachmentImages corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TaskAttachmentImagesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskAttachmentImages for query {}", query);
        return taskAttachmentImagesSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskAttachmentImagesMapper::toDto);
    }
}
