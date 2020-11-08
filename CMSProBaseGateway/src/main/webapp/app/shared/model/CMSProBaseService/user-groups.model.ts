import { IUserRoles } from '@/shared/model/CMSProBaseService/user-roles.model';

export interface IUserGroups {
  id?: string;
  code?: string;
  description?: string;
  userRoles?: IUserRoles[];
  accountAccountNumber?: string;
  accountId?: string;
  usersFullName?: string;
  usersId?: string;
}

export class UserGroups implements IUserGroups {
  constructor(
    public id?: string,
    public code?: string,
    public description?: string,
    public userRoles?: IUserRoles[],
    public accountAccountNumber?: string,
    public accountId?: string,
    public usersFullName?: string,
    public usersId?: string
  ) {}
}
