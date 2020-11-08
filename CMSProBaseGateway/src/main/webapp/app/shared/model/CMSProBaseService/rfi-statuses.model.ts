export interface IRFIStatuses {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
  rfiId?: string;
}

export class RFIStatuses implements IRFIStatuses {
  constructor(public id?: string, public code?: string, public description?: string, public isActive?: boolean, public rfiId?: string) {
    this.isActive = this.isActive || false;
  }
}
