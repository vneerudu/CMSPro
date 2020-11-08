package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.AccountStatuses;
import com.cmspro.base.microservice.repository.AccountStatusesRepository;
import com.cmspro.base.microservice.repository.search.AccountStatusesSearchRepository;
import com.cmspro.base.microservice.service.dto.AccountStatusesDTO;
import com.cmspro.base.microservice.service.mapper.AccountStatusesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AccountStatuses}.
 */
@Service
public class AccountStatusesService {

    private final Logger log = LoggerFactory.getLogger(AccountStatusesService.class);

    private final AccountStatusesRepository accountStatusesRepository;

    private final AccountStatusesMapper accountStatusesMapper;

    private final AccountStatusesSearchRepository accountStatusesSearchRepository;

    public AccountStatusesService(AccountStatusesRepository accountStatusesRepository, AccountStatusesMapper accountStatusesMapper, AccountStatusesSearchRepository accountStatusesSearchRepository) {
        this.accountStatusesRepository = accountStatusesRepository;
        this.accountStatusesMapper = accountStatusesMapper;
        this.accountStatusesSearchRepository = accountStatusesSearchRepository;
    }

    /**
     * Save a accountStatuses.
     *
     * @param accountStatusesDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountStatusesDTO save(AccountStatusesDTO accountStatusesDTO) {
        log.debug("Request to save AccountStatuses : {}", accountStatusesDTO);
        AccountStatuses accountStatuses = accountStatusesMapper.toEntity(accountStatusesDTO);
        accountStatuses = accountStatusesRepository.save(accountStatuses);
        AccountStatusesDTO result = accountStatusesMapper.toDto(accountStatuses);
        accountStatusesSearchRepository.save(accountStatuses);
        return result;
    }

    /**
     * Get all the accountStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AccountStatusesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountStatuses");
        return accountStatusesRepository.findAll(pageable)
            .map(accountStatusesMapper::toDto);
    }


    /**
     * Get one accountStatuses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AccountStatusesDTO> findOne(String id) {
        log.debug("Request to get AccountStatuses : {}", id);
        return accountStatusesRepository.findById(id)
            .map(accountStatusesMapper::toDto);
    }

    /**
     * Delete the accountStatuses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete AccountStatuses : {}", id);
        accountStatusesRepository.deleteById(id);
        accountStatusesSearchRepository.deleteById(id);
    }

    /**
     * Search for the accountStatuses corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AccountStatusesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AccountStatuses for query {}", query);
        return accountStatusesSearchRepository.search(queryStringQuery(query), pageable)
            .map(accountStatusesMapper::toDto);
    }
}
