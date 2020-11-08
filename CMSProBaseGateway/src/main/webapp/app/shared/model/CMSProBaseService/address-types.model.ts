export interface IAddressTypes {
  id?: string;
  code?: string;
  description?: string;
}

export class AddressTypes implements IAddressTypes {
  constructor(public id?: string, public code?: string, public description?: string) {}
}
