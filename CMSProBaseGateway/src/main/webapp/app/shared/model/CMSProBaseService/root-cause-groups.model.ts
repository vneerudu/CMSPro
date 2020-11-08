import { IRootCauses } from '@/shared/model/CMSProBaseService/root-causes.model';

export interface IRootCauseGroups {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
  rootCauses?: IRootCauses[];
}

export class RootCauseGroups implements IRootCauseGroups {
  constructor(
    public id?: string,
    public code?: string,
    public description?: string,
    public isActive?: boolean,
    public rootCauses?: IRootCauses[]
  ) {
    this.isActive = this.isActive || false;
  }
}
