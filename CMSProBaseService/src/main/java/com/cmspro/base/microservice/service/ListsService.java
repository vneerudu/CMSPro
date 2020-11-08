package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Lists;
import com.cmspro.base.microservice.repository.ListsRepository;
import com.cmspro.base.microservice.repository.search.ListsSearchRepository;
import com.cmspro.base.microservice.service.dto.ListsDTO;
import com.cmspro.base.microservice.service.mapper.ListsMapper;
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
 * Service Implementation for managing {@link Lists}.
 */
@Service
public class ListsService {

    private final Logger log = LoggerFactory.getLogger(ListsService.class);

    private final ListsRepository listsRepository;

    private final ListsMapper listsMapper;

    private final ListsSearchRepository listsSearchRepository;

    public ListsService(ListsRepository listsRepository, ListsMapper listsMapper, ListsSearchRepository listsSearchRepository) {
        this.listsRepository = listsRepository;
        this.listsMapper = listsMapper;
        this.listsSearchRepository = listsSearchRepository;
    }

    /**
     * Save a lists.
     *
     * @param listsDTO the entity to save.
     * @return the persisted entity.
     */
    public ListsDTO save(ListsDTO listsDTO) {
        log.debug("Request to save Lists : {}", listsDTO);
        Lists lists = listsMapper.toEntity(listsDTO);
        lists = listsRepository.save(lists);
        ListsDTO result = listsMapper.toDto(lists);
        listsSearchRepository.save(lists);
        return result;
    }

    /**
     * Get all the lists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ListsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lists");
        return listsRepository.findAll(pageable)
            .map(listsMapper::toDto);
    }



    /**
     *  Get all the lists where Task is {@code null}.
     *  @return the list of entities.
     */
    public List<ListsDTO> findAllWhereTaskIsNull() {
        log.debug("Request to get all lists where Task is null");
        return StreamSupport
            .stream(listsRepository.findAll().spliterator(), false)
            .filter(lists -> lists.getTask() == null)
            .map(listsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one lists by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ListsDTO> findOne(String id) {
        log.debug("Request to get Lists : {}", id);
        return listsRepository.findById(id)
            .map(listsMapper::toDto);
    }

    /**
     * Delete the lists by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Lists : {}", id);
        listsRepository.deleteById(id);
        listsSearchRepository.deleteById(id);
    }

    /**
     * Search for the lists corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ListsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Lists for query {}", query);
        return listsSearchRepository.search(queryStringQuery(query), pageable)
            .map(listsMapper::toDto);
    }
}
