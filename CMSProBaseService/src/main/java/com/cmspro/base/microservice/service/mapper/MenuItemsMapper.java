package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.MenuItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MenuItems} and its DTO {@link MenuItemsDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserRolesMapper.class})
public interface MenuItemsMapper extends EntityMapper<MenuItemsDTO, MenuItems> {

    @Mapping(source = "userRoles.id", target = "userRolesId")
    @Mapping(source = "userRoles.description", target = "userRolesDescription")
    MenuItemsDTO toDto(MenuItems menuItems);

    @Mapping(source = "userRolesId", target = "userRoles")
    MenuItems toEntity(MenuItemsDTO menuItemsDTO);

    default MenuItems fromId(String id) {
        if (id == null) {
            return null;
        }
        MenuItems menuItems = new MenuItems();
        menuItems.setId(id);
        return menuItems;
    }
}
