export interface ITaskLocations {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
  taskId?: string;
}

export class TaskLocations implements ITaskLocations {
  constructor(public id?: string, public code?: string, public description?: string, public isActive?: boolean, public taskId?: string) {
    this.isActive = this.isActive || false;
  }
}
