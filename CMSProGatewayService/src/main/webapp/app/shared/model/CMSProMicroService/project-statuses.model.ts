export interface IProjectStatuses {
  id?: number;
  status_id?: number;
  status_code?: string;
  description?: string;
  is_active?: boolean;
}

export class ProjectStatuses implements IProjectStatuses {
  constructor(
    public id?: number,
    public status_id?: number,
    public status_code?: string,
    public description?: string,
    public is_active?: boolean
  ) {
    this.is_active = this.is_active || false;
  }
}
