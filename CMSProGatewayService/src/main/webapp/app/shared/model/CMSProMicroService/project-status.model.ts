export interface IProjectStatus {
  id?: number;
  code?: string;
  description?: string;
  isActive?: boolean;
}

export class ProjectStatus implements IProjectStatus {
  constructor(public id?: number, public code?: string, public description?: string, public isActive?: boolean) {
    this.isActive = this.isActive || false;
  }
}
