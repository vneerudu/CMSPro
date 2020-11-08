export interface IAccountStatuses {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
}

export class AccountStatuses implements IAccountStatuses {
  constructor(public id?: string, public code?: string, public description?: string, public isActive?: boolean) {
    this.isActive = this.isActive || false;
  }
}
