export interface IRootCauses {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
  taskId?: string;
  groupsCode?: string;
  groupsId?: string;
}

export class RootCauses implements IRootCauses {
  constructor(
    public id?: string,
    public code?: string,
    public description?: string,
    public isActive?: boolean,
    public taskId?: string,
    public groupsCode?: string,
    public groupsId?: string
  ) {
    this.isActive = this.isActive || false;
  }
}
