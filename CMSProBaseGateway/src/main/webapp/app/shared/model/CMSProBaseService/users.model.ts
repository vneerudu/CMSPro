import { IUserGroups } from '@/shared/model/CMSProBaseService/user-groups.model';
import { IAddresses } from '@/shared/model/CMSProBaseService/addresses.model';

export interface IUsers {
  id?: string;
  firstName?: string;
  lastName?: string;
  fullName?: string;
  prefix?: string;
  emailAddress?: string;
  phoneNumber?: string;
  title?: string;
  company?: string;
  trackLocation?: boolean;
  userGroups?: IUserGroups[];
  addresses?: IAddresses[];
  accountAccountNumber?: string;
  accountId?: string;
  teamName?: string;
  teamId?: string;
  taskAssignedTitle?: string;
  taskAssignedId?: string;
  taskToMonitorTitle?: string;
  taskToMonitorId?: string;
}

export class Users implements IUsers {
  constructor(
    public id?: string,
    public firstName?: string,
    public lastName?: string,
    public fullName?: string,
    public prefix?: string,
    public emailAddress?: string,
    public phoneNumber?: string,
    public title?: string,
    public company?: string,
    public trackLocation?: boolean,
    public userGroups?: IUserGroups[],
    public addresses?: IAddresses[],
    public accountAccountNumber?: string,
    public accountId?: string,
    public teamName?: string,
    public teamId?: string,
    public taskAssignedTitle?: string,
    public taskAssignedId?: string,
    public taskToMonitorTitle?: string,
    public taskToMonitorId?: string
  ) {
    this.trackLocation = this.trackLocation || false;
  }
}
