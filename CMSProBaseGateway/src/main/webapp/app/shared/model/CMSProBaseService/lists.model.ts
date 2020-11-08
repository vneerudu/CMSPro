export interface ILists {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
  taskId?: string;
  projectName?: string;
  projectId?: string;
}

export class Lists implements ILists {
  constructor(
    public id?: string,
    public code?: string,
    public description?: string,
    public isActive?: boolean,
    public taskId?: string,
    public projectName?: string,
    public projectId?: string
  ) {
    this.isActive = this.isActive || false;
  }
}
