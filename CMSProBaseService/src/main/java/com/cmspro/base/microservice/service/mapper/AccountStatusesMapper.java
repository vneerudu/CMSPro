package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.AccountStatusesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountStatuses} and its DTO {@link AccountStatusesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountStatusesMapper extends EntityMapper<AccountStatusesDTO, AccountStatuses> {



    default AccountStatuses fromId(String id) {
        if (id == null) {
            return null;
        }
        AccountStatuses accountStatuses = new AccountStatuses();
        accountStatuses.setId(id);
        return accountStatuses;
    }
}
