import { IUserPermissions } from '@/shared/model/CMSProBaseService/user-permissions.model';
import { IMenuItems } from '@/shared/model/CMSProBaseService/menu-items.model';

export interface IUserRoles {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
  permissions?: IUserPermissions[];
  menuItems?: IMenuItems[];
  userGroupDescription?: string;
  userGroupId?: string;
}

export class UserRoles implements IUserRoles {
  constructor(
    public id?: string,
    public code?: string,
    public description?: string,
    public isActive?: boolean,
    public permissions?: IUserPermissions[],
    public menuItems?: IMenuItems[],
    public userGroupDescription?: string,
    public userGroupId?: string
  ) {
    this.isActive = this.isActive || false;
  }
}
