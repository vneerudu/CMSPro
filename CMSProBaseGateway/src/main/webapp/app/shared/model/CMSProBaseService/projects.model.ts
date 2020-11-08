import { IAddresses } from '@/shared/model/CMSProBaseService/addresses.model';
import { ISheets } from '@/shared/model/CMSProBaseService/sheets.model';
import { IProjectTeams } from '@/shared/model/CMSProBaseService/project-teams.model';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';
import { IStamps } from '@/shared/model/CMSProBaseService/stamps.model';
import { ILists } from '@/shared/model/CMSProBaseService/lists.model';
import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';

export interface IProjects {
  id?: string;
  code?: string;
  name?: string;
  startDate?: Date;
  finishDate?: Date;
  lastUpdate?: Date;
  createdBy?: string;
  creationDate?: Date;
  addresses?: IAddresses[];
  sheets?: ISheets[];
  teams?: IProjectTeams[];
  tasks?: ITasks[];
  stamps?: IStamps[];
  lists?: ILists[];
  rfis?: IRFI[];
  accountAccountNumber?: string;
  accountId?: string;
}

export class Projects implements IProjects {
  constructor(
    public id?: string,
    public code?: string,
    public name?: string,
    public startDate?: Date,
    public finishDate?: Date,
    public lastUpdate?: Date,
    public createdBy?: string,
    public creationDate?: Date,
    public addresses?: IAddresses[],
    public sheets?: ISheets[],
    public teams?: IProjectTeams[],
    public tasks?: ITasks[],
    public stamps?: IStamps[],
    public lists?: ILists[],
    public rfis?: IRFI[],
    public accountAccountNumber?: string,
    public accountId?: string
  ) {}
}
