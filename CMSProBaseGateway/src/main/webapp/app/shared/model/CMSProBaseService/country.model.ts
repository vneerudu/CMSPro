export interface ICountry {
  id?: string;
  code?: string;
  description?: string;
}

export class Country implements ICountry {
  constructor(public id?: string, public code?: string, public description?: string) {}
}
