export interface IProjects {
  id?: number;
  projectID?: number;
  name?: string;
  department?: string;
  organization?: string;
  startDate?: Date;
  finishDate?: Date;
  projectStatusRelDescription?: string;
  projectStatusRelId?: number;
}

export class Projects implements IProjects {
  constructor(
    public id?: number,
    public projectID?: number,
    public name?: string,
    public department?: string,
    public organization?: string,
    public startDate?: Date,
    public finishDate?: Date,
    public projectStatusRelDescription?: string,
    public projectStatusRelId?: number
  ) {}
}
