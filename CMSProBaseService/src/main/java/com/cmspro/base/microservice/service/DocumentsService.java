package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Documents;
import com.cmspro.base.microservice.repository.DocumentsRepository;
import com.cmspro.base.microservice.repository.search.DocumentsSearchRepository;
import com.cmspro.base.microservice.service.dto.DocumentsDTO;
import com.cmspro.base.microservice.service.mapper.DocumentsMapper;
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
 * Service Implementation for managing {@link Documents}.
 */
@Service
public class DocumentsService {

    private final Logger log = LoggerFactory.getLogger(DocumentsService.class);

    private final DocumentsRepository documentsRepository;

    private final DocumentsMapper documentsMapper;

    private final DocumentsSearchRepository documentsSearchRepository;

    public DocumentsService(DocumentsRepository documentsRepository, DocumentsMapper documentsMapper, DocumentsSearchRepository documentsSearchRepository) {
        this.documentsRepository = documentsRepository;
        this.documentsMapper = documentsMapper;
        this.documentsSearchRepository = documentsSearchRepository;
    }

    /**
     * Save a documents.
     *
     * @param documentsDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentsDTO save(DocumentsDTO documentsDTO) {
        log.debug("Request to save Documents : {}", documentsDTO);
        Documents documents = documentsMapper.toEntity(documentsDTO);
        documents = documentsRepository.save(documents);
        DocumentsDTO result = documentsMapper.toDto(documents);
        documentsSearchRepository.save(documents);
        return result;
    }

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<DocumentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Documents");
        return documentsRepository.findAll(pageable)
            .map(documentsMapper::toDto);
    }



    /**
     *  Get all the documents where Sheets is {@code null}.
     *  @return the list of entities.
     */
    public List<DocumentsDTO> findAllWhereSheetsIsNull() {
        log.debug("Request to get all documents where Sheets is null");
        return StreamSupport
            .stream(documentsRepository.findAll().spliterator(), false)
            .filter(documents -> documents.getSheets() == null)
            .map(documentsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one documents by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DocumentsDTO> findOne(String id) {
        log.debug("Request to get Documents : {}", id);
        return documentsRepository.findById(id)
            .map(documentsMapper::toDto);
    }

    /**
     * Delete the documents by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Documents : {}", id);
        documentsRepository.deleteById(id);
        documentsSearchRepository.deleteById(id);
    }

    /**
     * Search for the documents corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<DocumentsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Documents for query {}", query);
        return documentsSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentsMapper::toDto);
    }
}
