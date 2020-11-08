import { IUsers } from '@/shared/model/CMSProBaseService/users.model';
import { IUserGroups } from '@/shared/model/CMSProBaseService/user-groups.model';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';

export interface IAccounts {
  id?: string;
  accountNumber?: number;
  firstName?: string;
  lastName?: string;
  emailAddress?: string;
  phoneNumber?: string;
  creationDate?: Date;
  createdBy?: string;
  languageCode?: string;
  languageId?: string;
  logoFileName?: string;
  logoId?: string;
  statusDescription?: string;
  statusId?: string;
  users?: IUsers[];
  groups?: IUserGroups[];
  projects?: IProjects[];
}

export class Accounts implements IAccounts {
  constructor(
    public id?: string,
    public accountNumber?: number,
    public firstName?: string,
    public lastName?: string,
    public emailAddress?: string,
    public phoneNumber?: string,
    public creationDate?: Date,
    public createdBy?: string,
    public languageCode?: string,
    public languageId?: string,
    public logoFileName?: string,
    public logoId?: string,
    public statusDescription?: string,
    public statusId?: string,
    public users?: IUsers[],
    public groups?: IUserGroups[],
    public projects?: IProjects[]
  ) {}
}
