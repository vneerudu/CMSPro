export interface ILanguages {
  id?: string;
  code?: string;
  description?: string;
}

export class Languages implements ILanguages {
  constructor(public id?: string, public code?: string, public description?: string) {}
}
