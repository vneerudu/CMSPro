export interface ITaskAttachments {
  id?: string;
  folder?: string;
  fileName?: string;
  createdBy?: string;
  creationDate?: Date;
}

export class TaskAttachments implements ITaskAttachments {
  constructor(
    public id?: string,
    public folder?: string,
    public fileName?: string,
    public createdBy?: string,
    public creationDate?: Date
  ) {}
}
