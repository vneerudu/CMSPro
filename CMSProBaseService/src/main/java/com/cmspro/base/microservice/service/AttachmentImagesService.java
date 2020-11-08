package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.AttachmentImages;
import com.cmspro.base.microservice.repository.AttachmentImagesRepository;
import com.cmspro.base.microservice.repository.search.AttachmentImagesSearchRepository;
import com.cmspro.base.microservice.service.dto.AttachmentImagesDTO;
import com.cmspro.base.microservice.service.mapper.AttachmentImagesMapper;
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
 * Service Implementation for managing {@link AttachmentImages}.
 */
@Service
public class AttachmentImagesService {

    private final Logger log = LoggerFactory.getLogger(AttachmentImagesService.class);

    private final AttachmentImagesRepository attachmentImagesRepository;

    private final AttachmentImagesMapper attachmentImagesMapper;

    private final AttachmentImagesSearchRepository attachmentImagesSearchRepository;

    public AttachmentImagesService(AttachmentImagesRepository attachmentImagesRepository, AttachmentImagesMapper attachmentImagesMapper, AttachmentImagesSearchRepository attachmentImagesSearchRepository) {
        this.attachmentImagesRepository = attachmentImagesRepository;
        this.attachmentImagesMapper = attachmentImagesMapper;
        this.attachmentImagesSearchRepository = attachmentImagesSearchRepository;
    }

    /**
     * Save a attachmentImages.
     *
     * @param attachmentImagesDTO the entity to save.
     * @return the persisted entity.
     */
    public AttachmentImagesDTO save(AttachmentImagesDTO attachmentImagesDTO) {
        log.debug("Request to save AttachmentImages : {}", attachmentImagesDTO);
        AttachmentImages attachmentImages = attachmentImagesMapper.toEntity(attachmentImagesDTO);
        attachmentImages = attachmentImagesRepository.save(attachmentImages);
        AttachmentImagesDTO result = attachmentImagesMapper.toDto(attachmentImages);
        attachmentImagesSearchRepository.save(attachmentImages);
        return result;
    }

    /**
     * Get all the attachmentImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AttachmentImagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachmentImages");
        return attachmentImagesRepository.findAll(pageable)
            .map(attachmentImagesMapper::toDto);
    }



    /**
     *  Get all the attachmentImages where Attachment is {@code null}.
     *  @return the list of entities.
     */
    public List<AttachmentImagesDTO> findAllWhereAttachmentIsNull() {
        log.debug("Request to get all attachmentImages where Attachment is null");
        return StreamSupport
            .stream(attachmentImagesRepository.findAll().spliterator(), false)
            .filter(attachmentImages -> attachmentImages.getAttachment() == null)
            .map(attachmentImagesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one attachmentImages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AttachmentImagesDTO> findOne(String id) {
        log.debug("Request to get AttachmentImages : {}", id);
        return attachmentImagesRepository.findById(id)
            .map(attachmentImagesMapper::toDto);
    }

    /**
     * Delete the attachmentImages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete AttachmentImages : {}", id);
        attachmentImagesRepository.deleteById(id);
        attachmentImagesSearchRepository.deleteById(id);
    }

    /**
     * Search for the attachmentImages corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AttachmentImagesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AttachmentImages for query {}", query);
        return attachmentImagesSearchRepository.search(queryStringQuery(query), pageable)
            .map(attachmentImagesMapper::toDto);
    }
}
