export interface ITaskTypes {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
  taskId?: string;
}

export class TaskTypes implements ITaskTypes {
  constructor(public id?: string, public code?: string, public description?: string, public isActive?: boolean, public taskId?: string) {
    this.isActive = this.isActive || false;
  }
}
