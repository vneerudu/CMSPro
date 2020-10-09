export interface IProject {
  id?: number;
  name?: string;
  organization?: string;
  startDate?: Date;
  finishDate?: Date;
}

export class Project implements IProject {
  constructor(public id?: number, public name?: string, public organization?: string, public startDate?: Date, public finishDate?: Date) {}
}
