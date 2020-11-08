export interface ITaskComments {
  id?: string;
  createdBy?: string;
  comment?: string;
  creationDate?: Date;
  taskTitle?: string;
  taskId?: string;
}

export class TaskComments implements ITaskComments {
  constructor(
    public id?: string,
    public createdBy?: string,
    public comment?: string,
    public creationDate?: Date,
    public taskTitle?: string,
    public taskId?: string
  ) {}
}
