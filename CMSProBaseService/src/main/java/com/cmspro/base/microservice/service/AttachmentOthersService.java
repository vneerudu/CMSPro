package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.AttachmentOthers;
import com.cmspro.base.microservice.repository.AttachmentOthersRepository;
import com.cmspro.base.microservice.repository.search.AttachmentOthersSearchRepository;
import com.cmspro.base.microservice.service.dto.AttachmentOthersDTO;
import com.cmspro.base.microservice.service.mapper.AttachmentOthersMapper;
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
 * Service Implementation for managing {@link AttachmentOthers}.
 */
@Service
public class AttachmentOthersService {

    private final Logger log = LoggerFactory.getLogger(AttachmentOthersService.class);

    private final AttachmentOthersRepository attachmentOthersRepository;

    private final AttachmentOthersMapper attachmentOthersMapper;

    private final AttachmentOthersSearchRepository attachmentOthersSearchRepository;

    public AttachmentOthersService(AttachmentOthersRepository attachmentOthersRepository, AttachmentOthersMapper attachmentOthersMapper, AttachmentOthersSearchRepository attachmentOthersSearchRepository) {
        this.attachmentOthersRepository = attachmentOthersRepository;
        this.attachmentOthersMapper = attachmentOthersMapper;
        this.attachmentOthersSearchRepository = attachmentOthersSearchRepository;
    }

    /**
     * Save a attachmentOthers.
     *
     * @param attachmentOthersDTO the entity to save.
     * @return the persisted entity.
     */
    public AttachmentOthersDTO save(AttachmentOthersDTO attachmentOthersDTO) {
        log.debug("Request to save AttachmentOthers : {}", attachmentOthersDTO);
        AttachmentOthers attachmentOthers = attachmentOthersMapper.toEntity(attachmentOthersDTO);
        attachmentOthers = attachmentOthersRepository.save(attachmentOthers);
        AttachmentOthersDTO result = attachmentOthersMapper.toDto(attachmentOthers);
        attachmentOthersSearchRepository.save(attachmentOthers);
        return result;
    }

    /**
     * Get all the attachmentOthers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AttachmentOthersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachmentOthers");
        return attachmentOthersRepository.findAll(pageable)
            .map(attachmentOthersMapper::toDto);
    }



    /**
     *  Get all the attachmentOthers where Attachment is {@code null}.
     *  @return the list of entities.
     */
    public List<AttachmentOthersDTO> findAllWhereAttachmentIsNull() {
        log.debug("Request to get all attachmentOthers where Attachment is null");
        return StreamSupport
            .stream(attachmentOthersRepository.findAll().spliterator(), false)
            .filter(attachmentOthers -> attachmentOthers.getAttachment() == null)
            .map(attachmentOthersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one attachmentOthers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AttachmentOthersDTO> findOne(String id) {
        log.debug("Request to get AttachmentOthers : {}", id);
        return attachmentOthersRepository.findById(id)
            .map(attachmentOthersMapper::toDto);
    }

    /**
     * Delete the attachmentOthers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete AttachmentOthers : {}", id);
        attachmentOthersRepository.deleteById(id);
        attachmentOthersSearchRepository.deleteById(id);
    }

    /**
     * Search for the attachmentOthers corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AttachmentOthersDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AttachmentOthers for query {}", query);
        return attachmentOthersSearchRepository.search(queryStringQuery(query), pageable)
            .map(attachmentOthersMapper::toDto);
    }
}
