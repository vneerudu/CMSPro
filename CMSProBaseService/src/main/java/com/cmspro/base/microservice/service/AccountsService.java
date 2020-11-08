package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Accounts;
import com.cmspro.base.microservice.repository.AccountsRepository;
import com.cmspro.base.microservice.repository.search.AccountsSearchRepository;
import com.cmspro.base.microservice.service.dto.AccountsDTO;
import com.cmspro.base.microservice.service.mapper.AccountsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Accounts}.
 */
@Service
public class AccountsService {

    private final Logger log = LoggerFactory.getLogger(AccountsService.class);

    private final AccountsRepository accountsRepository;

    private final AccountsMapper accountsMapper;

    private final AccountsSearchRepository accountsSearchRepository;

    public AccountsService(AccountsRepository accountsRepository, AccountsMapper accountsMapper, AccountsSearchRepository accountsSearchRepository) {
        this.accountsRepository = accountsRepository;
        this.accountsMapper = accountsMapper;
        this.accountsSearchRepository = accountsSearchRepository;
    }

    /**
     * Save a accounts.
     *
     * @param accountsDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountsDTO save(AccountsDTO accountsDTO) {
        log.debug("Request to save Accounts : {}", accountsDTO);
        Accounts accounts = accountsMapper.toEntity(accountsDTO);
        accounts = accountsRepository.save(accounts);
        AccountsDTO result = accountsMapper.toDto(accounts);
        accountsSearchRepository.save(accounts);
        return result;
    }

    /**
     * Get all the accounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AccountsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accounts");
        return accountsRepository.findAll(pageable)
            .map(accountsMapper::toDto);
    }


    /**
     * Get one accounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AccountsDTO> findOne(String id) {
        log.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id)
            .map(accountsMapper::toDto);
    }

    /**
     * Delete the accounts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
        accountsSearchRepository.deleteById(id);
    }

    /**
     * Search for the accounts corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AccountsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Accounts for query {}", query);
        return accountsSearchRepository.search(queryStringQuery(query), pageable)
            .map(accountsMapper::toDto);
    }
}
