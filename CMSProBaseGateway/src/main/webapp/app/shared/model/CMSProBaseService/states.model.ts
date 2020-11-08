export interface IStates {
  id?: string;
  code?: string;
  description?: string;
}

export class States implements IStates {
  constructor(public id?: string, public code?: string, public description?: string) {}
}
