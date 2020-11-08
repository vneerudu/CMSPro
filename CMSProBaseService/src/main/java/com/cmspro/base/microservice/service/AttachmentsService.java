package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Attachments;
import com.cmspro.base.microservice.repository.AttachmentsRepository;
import com.cmspro.base.microservice.repository.search.AttachmentsSearchRepository;
import com.cmspro.base.microservice.service.dto.AttachmentsDTO;
import com.cmspro.base.microservice.service.mapper.AttachmentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Attachments}.
 */
@Service
public class AttachmentsService {

    private final Logger log = LoggerFactory.getLogger(AttachmentsService.class);

    private final AttachmentsRepository attachmentsRepository;

    private final AttachmentsMapper attachmentsMapper;

    private final AttachmentsSearchRepository attachmentsSearchRepository;

    public AttachmentsService(AttachmentsRepository attachmentsRepository, AttachmentsMapper attachmentsMapper, AttachmentsSearchRepository attachmentsSearchRepository) {
        this.attachmentsRepository = attachmentsRepository;
        this.attachmentsMapper = attachmentsMapper;
        this.attachmentsSearchRepository = attachmentsSearchRepository;
    }

    /**
     * Save a attachments.
     *
     * @param attachmentsDTO the entity to save.
     * @return the persisted entity.
     */
    public AttachmentsDTO save(AttachmentsDTO attachmentsDTO) {
        log.debug("Request to save Attachments : {}", attachmentsDTO);
        Attachments attachments = attachmentsMapper.toEntity(attachmentsDTO);
        attachments = attachmentsRepository.save(attachments);
        AttachmentsDTO result = attachmentsMapper.toDto(attachments);
        attachmentsSearchRepository.save(attachments);
        return result;
    }

    /**
     * Get all the attachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AttachmentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attachments");
        return attachmentsRepository.findAll(pageable)
            .map(attachmentsMapper::toDto);
    }


    /**
     * Get one attachments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AttachmentsDTO> findOne(String id) {
        log.debug("Request to get Attachments : {}", id);
        return attachmentsRepository.findById(id)
            .map(attachmentsMapper::toDto);
    }

    /**
     * Delete the attachments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Attachments : {}", id);
        attachmentsRepository.deleteById(id);
        attachmentsSearchRepository.deleteById(id);
    }

    /**
     * Search for the attachments corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AttachmentsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Attachments for query {}", query);
        return attachmentsSearchRepository.search(queryStringQuery(query), pageable)
            .map(attachmentsMapper::toDto);
    }
}
