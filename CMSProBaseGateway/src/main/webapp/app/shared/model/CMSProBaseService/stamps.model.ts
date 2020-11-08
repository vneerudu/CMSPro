export interface IStamps {
  id?: string;
  stamp?: string;
  title?: string;
  createdBy?: string;
  creationDate?: Date;
  taskId?: string;
  projectName?: string;
  projectId?: string;
}

export class Stamps implements IStamps {
  constructor(
    public id?: string,
    public stamp?: string,
    public title?: string,
    public createdBy?: string,
    public creationDate?: Date,
    public taskId?: string,
    public projectName?: string,
    public projectId?: string
  ) {}
}
