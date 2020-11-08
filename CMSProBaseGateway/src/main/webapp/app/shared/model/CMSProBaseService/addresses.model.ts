export interface IAddresses {
  id?: string;
  address1?: string;
  address2?: string;
  city?: string;
  zipCode?: number;
  isActive?: boolean;
  createdBy?: string;
  creationDate?: Date;
  addressTypeDescription?: string;
  addressTypeId?: string;
  stateDescription?: string;
  stateId?: string;
  countryDescription?: string;
  countryId?: string;
  userFullName?: string;
  userId?: string;
  projectName?: string;
  projectId?: string;
}

export class Addresses implements IAddresses {
  constructor(
    public id?: string,
    public address1?: string,
    public address2?: string,
    public city?: string,
    public zipCode?: number,
    public isActive?: boolean,
    public createdBy?: string,
    public creationDate?: Date,
    public addressTypeDescription?: string,
    public addressTypeId?: string,
    public stateDescription?: string,
    public stateId?: string,
    public countryDescription?: string,
    public countryId?: string,
    public userFullName?: string,
    public userId?: string,
    public projectName?: string,
    public projectId?: string
  ) {
    this.isActive = this.isActive || false;
  }
}
