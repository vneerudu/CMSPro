package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.AccountsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accounts} and its DTO {@link AccountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {LanguagesMapper.class, LogosMapper.class, AccountStatusesMapper.class})
public interface AccountsMapper extends EntityMapper<AccountsDTO, Accounts> {

    @Mapping(source = "language.id", target = "languageId")
    @Mapping(source = "language.code", target = "languageCode")
    @Mapping(source = "logo.id", target = "logoId")
    @Mapping(source = "logo.fileName", target = "logoFileName")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.description", target = "statusDescription")
    AccountsDTO toDto(Accounts accounts);

    @Mapping(source = "languageId", target = "language")
    @Mapping(source = "logoId", target = "logo")
    @Mapping(source = "statusId", target = "status")
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "removeGroups", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "removeProject", ignore = true)
    Accounts toEntity(AccountsDTO accountsDTO);

    default Accounts fromId(String id) {
        if (id == null) {
            return null;
        }
        Accounts accounts = new Accounts();
        accounts.setId(id);
        return accounts;
    }
}
