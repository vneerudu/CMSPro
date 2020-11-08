package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.AddressesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Addresses} and its DTO {@link AddressesDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressTypesMapper.class, StatesMapper.class, CountryMapper.class, UsersMapper.class, ProjectsMapper.class})
public interface AddressesMapper extends EntityMapper<AddressesDTO, Addresses> {

    @Mapping(source = "addressType.id", target = "addressTypeId")
    @Mapping(source = "addressType.description", target = "addressTypeDescription")
    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "state.description", target = "stateDescription")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.description", target = "countryDescription")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userFullName")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    AddressesDTO toDto(Addresses addresses);

    @Mapping(source = "addressTypeId", target = "addressType")
    @Mapping(source = "stateId", target = "state")
    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "projectId", target = "project")
    Addresses toEntity(AddressesDTO addressesDTO);

    default Addresses fromId(String id) {
        if (id == null) {
            return null;
        }
        Addresses addresses = new Addresses();
        addresses.setId(id);
        return addresses;
    }
}
