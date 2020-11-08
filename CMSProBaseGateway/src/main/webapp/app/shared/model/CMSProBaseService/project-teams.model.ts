import { IUsers } from '@/shared/model/CMSProBaseService/users.model';

export interface IProjectTeams {
  id?: string;
  name?: string;
  createdBy?: string;
  creationDate?: Date;
  users?: IUsers[];
  projectName?: string;
  projectId?: string;
}

export class ProjectTeams implements IProjectTeams {
  constructor(
    public id?: string,
    public name?: string,
    public createdBy?: string,
    public creationDate?: Date,
    public users?: IUsers[],
    public projectName?: string,
    public projectId?: string
  ) {}
}
